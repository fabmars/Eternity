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

public class PuzzleDao extends GenericDao<Piece> {

  private static final String PREFIX_FACES = "Faces:";
  private static final String PREFIX_PIECES = "Pieces:";
  private final static OrientationAdapter oa = new OrientationAdapter();

  private Path stateFile;
  private Puzzle puzzle;
  private ItemStore<Face> faceStore;
  private ItemStore<Piece> pieceStore;
  
  
  public PuzzleDao(Path stateFile) {
    this.stateFile = stateFile;
  }

  
  /**
   * @return an existing game, loaded
   * @throws IOException
   */
  public CsvParseReport parse() throws IOException {
    return parse(0);
  }

  /**
   * @return an existing game, loaded
   * @throws IOException
   */
  public CsvParseReport parse(int maxErrors) throws IOException {
    try(BufferedReader br = Files.newBufferedReader(stateFile, charset)) {
      return parse(br, maxErrors);
    }
  }
  
  
  @Override
  public CsvParseReport parse(Reader reader, int maxErrors) throws IOException {
    CsvParseReport result = new CsvParseReport(maxErrors);
    Path facesFile = null, piecesFile = null;

    try(BufferedReader br = new BufferedReader(reader)) {
      String line;
      while((line = br.readLine()) != null && (facesFile == null || piecesFile == null)) {
        if(!isComment(line)) {
          if(line.startsWith(PREFIX_PIECES)) {
            if(piecesFile == null) {
              String piecesFileName = line.substring(PREFIX_PIECES.length()).trim();
              piecesFile = stateFile.resolveSibling(piecesFileName);
            }
            else {
              //ERROR dupe pieces file
            }
          }
          else if(line.startsWith(PREFIX_FACES)) {
            if(facesFile == null) {
              String facesFileName = line.substring(PREFIX_FACES.length()).trim();
              facesFile = stateFile.resolveSibling(facesFileName);
            }
            else {
              //ERROR dupe faces file
            }
          }
          else {
            //ERROR unknown line type
          }
        }
      }
      
      //if we're reaching here, that means facesFile != null && piecesFile != null
      faceStore = loadFaces(facesFile);
      pieceStore = loadPieces(piecesFile, faceStore);
      
      if(pieceStore.isEmpty()) {
        result.addError(new CsvException("Empty pieces file: " + piecesFile));
      }
      else if(!pieceStore.isUnicity()) {
        result.addError(new CsvException("Pieces are not unique: " + piecesFile));
      }
      else {
        int size = pieceStore.size();
        //FIXME List<Integer> factor = primeFactors(pieceCount);
        puzzle = new Puzzle(4, 4);
        return super.parse(br, maxErrors);
      }
    }
    return result;
  }

  //FIXME throw exception if 1
  public ItemStore<Face> loadFaces(Path facesFile) throws IOException {
    ItemStore<Face> fs = new ItemStore<Face>();
    
    try(BufferedReader br = Files.newBufferedReader(facesFile, charset)) {
      new FaceDao(fs).parse(br);
    }
    return fs;
  }

  //FIXME throw exception if 1
  public ItemStore<Piece> loadPieces(Path piecesFile, ItemStore<Face> fs) throws IOException {
    PieceDao pieceDao = new PieceDao(fs);
    try(BufferedReader br = Files.newBufferedReader(piecesFile, charset)) {
      pieceDao.parse(br);
    }
    return pieceDao.getPieces();
  }
  
  
  @Override
  public Piece parseLine(String[] parts) throws CsvException {
    int i = 1; //skipping first P
    int id = Integer.parseInt(parts[i++].trim());
    int x = Integer.parseInt(parts[i++]) - 1; //1-based
    int y = Integer.parseInt(parts[i++]) - 1; //1-based
    Orientation orientation = oa.getAsObject(parts[i++]);
    
    Piece piece = new Piece(pieceStore.get(id), orientation); //copying because I want to keep the piece store as a reference, and/or there might be several players using it at the same time
    puzzle.setPiece(piece, y, x); //doing "insert" here because I need {x,y}
    return piece;
  }

  @Override
  public void insert(Piece piece) {
    //nothing, already done in parseLine
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
  
  public Puzzle getPuzzle() {
    return puzzle;
  }
}
