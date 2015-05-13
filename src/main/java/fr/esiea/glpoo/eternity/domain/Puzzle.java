package fr.esiea.glpoo.eternity.domain;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Puzzle extends ItemStore<Piece> {

  private final int rows, cols;
  private Path facesFile, piecesFile, stateFile;
  
  public Puzzle(int rows, int cols) {
    this(rows, cols, Puzzle.<Piece>emptyList(rows*cols));
  }
  
  public Puzzle(int rows, int cols, Collection<Piece> pieces) {
    this.rows = rows;
    this.cols = cols;
    setPieces(pieces);
  }

  public Puzzle(int rows, int cols, Piece[] pieces) {
    this(rows, cols, Arrays.asList(pieces));
  }

  private static <T> List<T> emptyList(int size) {
    List<T> list = new ArrayList<>(size);
    for(int i = 0; i < size; i++) {
      list.add(null);
    }
    return list;
  }
  
  private void setPieces(Collection<Piece> pieces) {
    int piecesCount = pieces.size();
    if(piecesCount != rows*cols) {
      throw new IllegalArgumentException("Bad pieces collection size (" + piecesCount + ") for puzzle[" + rows + "][" + cols +"]");
    }
    else {
      addAll(pieces);
    }
  }
  
  public Piece getPiece(int row, int col) {
    int index = getIndex(row, col);
    return items.get(index);
  }

  /**
   * @param piece null means there is no piece in the slot
   * @param row
   * @param col
   */
  public void setPiece(Piece piece, int row, int col) {
    int index = getIndex(row, col);
    items.set(index, piece);
  }
  
  private int getIndex(int row, int col) {
    return row * cols + col;
  }
  
  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public Path getStateFile() {
    return stateFile;
  }

  public void setStateFile(Path stateFile) {
    this.stateFile = stateFile;
  }

  public Path getFacesFile() {
    return facesFile;
  }

  public Path getPiecesFile() {
    return piecesFile;
  }

  public void setFacesFile(Path facesFile) {
    this.facesFile = facesFile;
  }

  public void setPiecesFile(Path piecesFile) {
    this.piecesFile = piecesFile;
  }
}
