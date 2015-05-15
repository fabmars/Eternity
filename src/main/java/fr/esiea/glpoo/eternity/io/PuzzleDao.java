package fr.esiea.glpoo.eternity.io;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import fr.esiea.glpoo.eternity.domain.FaceStore;
import fr.esiea.glpoo.eternity.domain.Orientation;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.PieceStore;
import fr.esiea.glpoo.eternity.domain.Puzzle;

//TODO load/save elapsed time too
public class PuzzleDao extends GenericDao<PieceCoordinates, PuzzleParseContext, Puzzle> {

  private static final String PREFIX_FACES = "Faces:";
  private static final String PREFIX_PIECES = "Pieces:";
  private final static OrientationAdapter oa = new OrientationAdapter();

  
  
  public PuzzleParseReport parse(URL stateFileUrl) throws IOException {
    return parse(stateFileUrl, DEFAULT_MAX_ERRORS);
  }
  
  public PuzzleParseReport parse(URL stateFileUrl, int maxErrors) throws IOException {
    try(InputStreamReader isr = new InputStreamReader(stateFileUrl.openStream(), charset)) {
      PuzzleParseContext context = new PuzzleParseContext(stateFileUrl);
      return parse(context, isr, maxErrors);
    }
  }
  
  
  @Override
  public PuzzleParseReport parse(PuzzleParseContext context, Reader reader, int maxErrors) throws IOException {
    URL stateFile = context.getStateFile();

    try(BufferedReader br = new BufferedReader(reader)) {
      String line;
      while((line = br.readLine()) != null) {
        if(!isComment(line)) {
          if(line.startsWith(PREFIX_PIECES)) {
            if(context.getPiecesFile() == null) {
              String piecesFileName = line.substring(PREFIX_PIECES.length()).trim();
              URL piecesFile = Utils.resolveSibling(stateFile, piecesFileName);
              context.setPiecesFile(piecesFile);
            }
            else {
              //FIXME ERROR dupe pieces file
            }
          }
          else if(line.startsWith(PREFIX_FACES)) {
            if(context.getFacesFile() == null) {
              String facesFileName = line.substring(PREFIX_FACES.length()).trim();
              URL facesFile = Utils.resolveSibling(stateFile, facesFileName);
              context.setFacesFile(facesFile);
            }
            else {
              //FIXME ERROR dupe faces file
            }
          }
          else {
            //FIXME ERROR unknown line type
          }
        }
        
        if(context.getFacesFile() != null && context.getPiecesFile() != null) {
          break;
        }
      }
      
      //if we're reaching here, that means facesFile != null && piecesFile != null
      
      return parseState(context, br, maxErrors);
    }
  }

  
  private PuzzleParseReport parseState(PuzzleParseContext context, BufferedReader br, int maxErrors) throws IOException {
    PuzzleParseReport finalReport = new PuzzleParseReport();
    CsvParseReport<PieceStore> facesAndPiecesReport = parseFacesAndPieces(context, maxErrors);
    finalReport.addErrors(facesAndPiecesReport.getErrors());
    
    if(!facesAndPiecesReport.isExceeded()) {
      CsvParseReport<Puzzle> puzzleParseReport = super.parse(context, br, maxErrors);
      finalReport.addErrors(puzzleParseReport.getErrors());
      finalReport.setPieces(facesAndPiecesReport.getOutcome());
      finalReport.setOutcome(puzzleParseReport.getOutcome());
    }
    return finalReport;
  }

  
  public CsvParseReport<PieceStore> parseFacesAndPieces(PuzzleParseContext context, int maxErrors) throws IOException {
    CsvParseReport<PieceStore> finalReport = new CsvParseReport<>(maxErrors);

    CsvParseReport<FaceStore> facesParseReport = loadFaces(context);
    finalReport.addErrors(facesParseReport.getErrors());
    if(!facesParseReport.isExceeded()){
      FaceStore faceStore = facesParseReport.getOutcome();
      context.setFaceStore(faceStore);
      
      CsvParseReport<PieceStore> piecesParseReport = loadPieces(context);
      finalReport.addErrors(piecesParseReport.getErrors());
      if(!piecesParseReport.isExceeded()) {

        PieceStore pieceStore = piecesParseReport.getOutcome();
        context.setPieceStore(pieceStore);
        
        if(pieceStore.isEmpty()) {
          finalReport.addError(new CsvException("Empty pieces file: " + context.getPiecesFile()));
        }
        else if(!pieceStore.isUnicity()) {
          finalReport.addError(new CsvException("Pieces are not unique: " + context.getPiecesFile()));
        }
        else {
          finalReport.setOutcome(pieceStore);
        }
      }
    }
    return finalReport;
  }

  public CsvParseReport<FaceStore> loadFaces(PuzzleParseContext context) throws IOException {
    try(InputStreamReader isr = new InputStreamReader(context.getFacesFile().openStream(), charset)) {
      return new FaceDao().parse(null, isr);
    }
  }

  public CsvParseReport<PieceStore> loadPieces(PuzzleParseContext context) throws IOException {
    try(InputStreamReader isr = new InputStreamReader(context.getPiecesFile().openStream(), charset)) {
      return new PieceDao().parse(context.getFaceStore(), isr);
    }
  }
  
  
  @Override
  public PieceCoordinates parseLine(PuzzleParseContext context, String[] parts) throws CsvException {
    int i = 1; //skipping first P
    int id = Integer.parseInt(parts[i++].trim());
    int x = Integer.parseInt(parts[i++]) - 1; //1-based
    int y = Integer.parseInt(parts[i++]) - 1; //1-based
    Orientation orientation = oa.getAsObject(parts[i++]);
    
    PieceStore pieceStore = context.getPieceStore();
    Piece piece = new Piece(pieceStore.get(id), orientation); //copying because I want to keep the piece store as a reference, and/or there might be several players using it at the same time
    return new PieceCoordinates(piece, x, y);
  }

  
  @Override
  public Puzzle createOutcome(PuzzleParseContext context) {
    int size = context.getPieceStore().size();
    //FIXME List<Integer> factor = primeFactors(pieceCount);
    Puzzle puzzle = new Puzzle(4, 4);
    
    puzzle.setFacesFile(context.getFacesFile());
    puzzle.setPiecesFile(context.getPiecesFile());
    puzzle.setStateFile(context.getStateFile());
    return puzzle;
  }

  @Override
  public void insert(Puzzle puzzle, PieceCoordinates pieceCoords) {
    puzzle.setPiece(pieceCoords.piece, pieceCoords.y, pieceCoords.x);
  }

  public static Dimension getBestDimension(List<Integer> primes) {
    int count = primes.size();
    if(count == 0) {
      return new Dimension(0, 0);
    }
    if(count == 1) {
      return new Dimension(primes.get(0), 1);
    }
    else if(count == 2) {
      return new Dimension(primes.get(0), primes.get(1));
    }
    else {
      return null;
      //FIXME first with last, etc
    }
  }
  
  public static List<Integer> primeFactors(int numbers) {
    int n = numbers;
    List<Integer> factors = new ArrayList<Integer>();
    for (int i = 2; i <= n / i; i++) {
      while (n % i == 0) {
        factors.add(i);
        n /= i;
      }
    }
    if (n > 1) {
      factors.add(n);
    }
    return factors;
  }
  
  
  public void save(Puzzle puzzle, Path stateFile) throws CsvException, IOException {
    try(BufferedWriter bw = Files.newBufferedWriter(stateFile, charset, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
      bw.append("# Pieces: nom_fichier");
      bw.newLine();
      bw.append("# Faces: nom_fichier");
      bw.newLine();
      bw.append("# P id_piece position_X position_Y orientation(Nord/Est/Sud/Ouest)");
      bw.newLine();
      
      
      bw.append(PREFIX_FACES).append(" ").append(Utils.getFileName(puzzle.getFacesFile())); //FIXME the faces file is supposed to be in the same folder as the state file
      bw.newLine();

      bw.append(PREFIX_PIECES).append(" ").append(Utils.getFileName(puzzle.getPiecesFile())); //FIXME idem
      bw.newLine();
      
      
      int i = 0, cols = puzzle.getCols();
      for(Piece piece : puzzle) {
        if(piece != null) {
          bw.append('P').append(';')
            .append(Integer.toString(piece.getId())).append(';')
            .append(Integer.toString(i%cols+1)).append(';')
            .append(Integer.toString(i/cols+1)).append(';')
            .append(oa.getAsString(piece.getOrientation()));
          bw.newLine();
        }        
        i++;
      }
      
      puzzle.setStateFile(stateFile.toUri().toURL());
    }
  }
}


