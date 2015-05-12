package fr.esiea.glpoo.eternity.io;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Orientation;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleDao extends GenericDao<PieceCoordinates, PuzzleParseContext, Puzzle> {

  private static final String PREFIX_FACES = "Faces:";
  private static final String PREFIX_PIECES = "Pieces:";
  private final static OrientationAdapter oa = new OrientationAdapter();

  
  /**
   * @return an existing game, loaded
   * @throws IOException
   */
  public PuzzleParseReport parse(Path stateFile) throws IOException {
    return parse(stateFile, 0);
  }

  /**
   * @return an existing game, loaded
   * @throws IOException
   */
  public PuzzleParseReport parse(Path stateFile, int maxErrors) throws IOException {
    try(BufferedReader br = Files.newBufferedReader(stateFile, charset)) {
      PuzzleParseContext context = new PuzzleParseContext(stateFile);
      return parse(context, br, maxErrors);
    }
  }
  
  
  @Override
  public PuzzleParseReport parse(PuzzleParseContext context, Reader reader, int maxErrors) throws IOException {
    Path stateFile = context.getStateFile();

    try(BufferedReader br = new BufferedReader(reader)) {
      String line;
      while((line = br.readLine()) != null) {
        if(!isComment(line)) {
          if(line.startsWith(PREFIX_PIECES)) {
            if(context.getPiecesFile() == null) {
              String piecesFileName = line.substring(PREFIX_PIECES.length()).trim();
              Path piecesFile = stateFile.resolveSibling(piecesFileName);
              context.setPiecesFile(piecesFile);
            }
            else {
              //FIXME ERROR dupe pieces file
            }
          }
          else if(line.startsWith(PREFIX_FACES)) {
            if(context.getFacesFile() == null) {
              String facesFileName = line.substring(PREFIX_FACES.length()).trim();
              Path facesFile = stateFile.resolveSibling(facesFileName);
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
    PuzzleParseReport finalReport = new PuzzleParseReport(maxErrors);

    CsvParseReport<ItemStore<Face>> facesParseReport = loadFaces(context);
    finalReport.addErrors(facesParseReport.getErrors());
    if(!facesParseReport.isExceeded()){
      ItemStore<Face> faceStore = facesParseReport.getOutcome();
      context.setFaceStore(faceStore);
      
      CsvParseReport<ItemStore<Piece>> piecesParseReport = loadPieces(context);
      finalReport.addErrors(piecesParseReport.getErrors());
      if(!piecesParseReport.isExceeded()) {

        Path piecesFile = context.getPiecesFile();
        ItemStore<Piece> pieceStore = piecesParseReport.getOutcome();
        context.setPieceStore(pieceStore);
        
        if(pieceStore.isEmpty()) {
          finalReport.addError(new CsvException("Empty pieces file: " + piecesFile));
        }
        else if(!pieceStore.isUnicity()) {
          finalReport.addError(new CsvException("Pieces are not unique: " + piecesFile));
        }
        else {
          CsvParseReport<Puzzle> puzzleParseReport = super.parse(context, br, maxErrors);
          finalReport.addErrors(puzzleParseReport.getErrors());
          finalReport.setPieces(pieceStore);
          finalReport.setOutcome(puzzleParseReport.getOutcome());
        }
      }
    }
    return finalReport;
  }

  //FIXME throw exception if 1
  public CsvParseReport<ItemStore<Face>> loadFaces(PuzzleParseContext context) throws IOException {
    try(BufferedReader br = Files.newBufferedReader(context.getFacesFile(), charset)) {
      return new FaceDao().parse(null, br);
    }
  }

  //FIXME throw exception if 1
  public CsvParseReport<ItemStore<Piece>> loadPieces(PuzzleParseContext context) throws IOException {
    try(BufferedReader br = Files.newBufferedReader(context.getPiecesFile(), charset)) {
      return new PieceDao().parse(context.getFaceStore(), br);
    }
  }
  
  
  @Override
  public PieceCoordinates parseLine(PuzzleParseContext context, String[] parts) throws CsvException {
    int i = 1; //skipping first P
    int id = Integer.parseInt(parts[i++].trim());
    int x = Integer.parseInt(parts[i++]) - 1; //1-based
    int y = Integer.parseInt(parts[i++]) - 1; //1-based
    Orientation orientation = oa.getAsObject(parts[i++]);
    
    ItemStore<Piece> pieceStore = context.getPieceStore();
    Piece piece = new Piece(pieceStore.get(id), orientation); //copying because I want to keep the piece store as a reference, and/or there might be several players using it at the same time
    return new PieceCoordinates(piece, x, y);
  }

  
  @Override
  public Puzzle createOutcome(PuzzleParseContext context) {
    int size = context.getPieceStore().size();
    //FIXME List<Integer> factor = primeFactors(pieceCount);
    return new Puzzle(4, 4);
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
}


