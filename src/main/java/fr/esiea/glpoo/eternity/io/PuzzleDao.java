package fr.esiea.glpoo.eternity.io;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.esiea.glpoo.eternity.domain.FaceStore;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleDao {

  public Charset cs = Charset.forName("UTF-8");

  /**
   * For an existing game to load
   * @param stateFile
   * @return
   * @throws IOException
   * @throws CsvException 
   */
  public Puzzle loadPuzzle(Path stateFile) throws IOException, CsvException {
    
    try(BufferedReader br = Files.newBufferedReader(stateFile, cs)) {
      Path facesFile = Paths.get("TODO");
      Path piecesFile = Paths.get("TODO");
      return loadPuzzle(facesFile, piecesFile);
    }
  }

  /**
   * For a new pazzle
   * @param facesFile
   * @param piecesFile
   * @param rows
   * @param cols
   * @return
   * @throws IOException
   * @throws CsvException 
   */
  public Puzzle loadPuzzle(Path facesFile, Path piecesFile) throws IOException, CsvException {
    FaceStore fs = loadFaces(facesFile);
    List<Piece> pieces = loadPieces(piecesFile, fs);

    int pieceCount = pieces.size();
    if(pieceCount > 0) {
      //FIXME List<Integer> factor = primeFactors(pieceCount);
      Puzzle puzzle = new Puzzle(4, 4, pieces); //FIXME
      return puzzle;
    }
    else {
      throw new CsvException("Empty pieces file: " + piecesFile);
    }
  }

  
  //FIXME throw exception if 1
  public FaceStore loadFaces(Path facesFile) throws IOException {
    FaceStore fs = new FaceStore();
    
    try(BufferedReader br = Files.newBufferedReader(facesFile, cs)) {
      new FaceDao(fs).parse(br);
    }
    return fs;
  }

  //FIXME throw exception if 1
  public List<Piece> loadPieces(Path piecesFile, FaceStore fs) throws IOException {
    PieceDao pieceDao = new PieceDao(fs);
    try(BufferedReader br = Files.newBufferedReader(piecesFile, cs)) {
      pieceDao.parse(br);
    }
    return pieceDao.getPieces();
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
