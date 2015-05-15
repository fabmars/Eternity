package fr.esiea.glpoo.eternity.domain;

import static fr.esiea.glpoo.eternity.domain.Orientation.EAST;
import static fr.esiea.glpoo.eternity.domain.Orientation.NORTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.SOUTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.WEST;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Puzzle extends ItemStore<Piece> {

  private final int rows, cols;
  private URL facesFile, piecesFile, stateFile;

  public Puzzle(int rows, int cols) {
    this(rows, cols, Puzzle.<Piece> emptyList(rows * cols));
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
    for (int i = 0; i < size; i++) {
      list.add(null);
    }
    return list;
  }

  private void setPieces(Collection<Piece> pieces) {
    int piecesCount = pieces.size();
    if (piecesCount != rows * cols) {
      throw new IllegalArgumentException("Bad pieces collection size (" + piecesCount + ") for puzzle[" + rows + "][" + cols + "]");
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
   * @param piece
   *          null means there is no piece in the slot
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

  public URL getStateFile() {
    return stateFile;
  }

  public void setStateFile(URL stateFile) {
    this.stateFile = stateFile;
  }

  public URL getFacesFile() {
    return facesFile;
  }

  public URL getPiecesFile() {
    return piecesFile;
  }

  public void setFacesFile(URL facesFile) {
    this.facesFile = facesFile;
  }

  public void setPiecesFile(URL piecesFile) {
    this.piecesFile = piecesFile;
  }
  
  public boolean isSolved() {
    //basic checks: if it ain't full, it ain't done :)
    for(Piece piece : this) {
      if(piece == null) {
        return false;
      }
    }
    
    //edge check: NORTH/SOUTH
    for(int c = 0; c < cols; c++) {
      if(!getPiece(0, c).getFace(NORTH).isEdge() || !getPiece(rows-1, c).getFace(SOUTH).isEdge()) {
        return false;
      }
    }
    //edge check: EAST/WEST
    for(int r = 0; r < rows; r++) {
      if(!getPiece(r, 0).getFace(WEST).isEdge() || !getPiece(r, cols-1).getFace(EAST).isEdge()) {
        return false;
      }
    }
    
    for(int r = 0; r < rows-1; r++) {
      for(int c = 0; c < cols-1; c++) {
        Piece p1 = getPiece(r, c);
        Piece p2 = getPiece(r, c+1);
        Piece p3 = getPiece(r+1, c);
        
        if(!p1.getFace(EAST).equals(p2.getFace(WEST))
        || !p1.getFace(SOUTH).equals(p3.getFace(NORTH))) {
          return false;
        }
      }
    }
    
    return true;
  }
}
