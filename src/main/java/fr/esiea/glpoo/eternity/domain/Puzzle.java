package fr.esiea.glpoo.eternity.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Puzzle {

  private Piece[][] pieces;
  private int rows, cols;
  
  public Puzzle(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
  }
  
  public Puzzle(int rows, int cols, Collection<Piece> pieces) {
    this(rows, cols);
    setPieces(pieces);
  }


  public Puzzle(int rows, int cols, Piece[] pieces) {
    this(rows, cols, Arrays.asList(pieces));
  }

  
  private void setPieces(Collection<Piece> pieces) {
    int piecesCount = pieces.size();
    if(piecesCount != rows*cols) {
      throw new IllegalArgumentException("Bad pieces list size (" + piecesCount + ") for puzzle[" + rows + "][" + cols +"]");
    }
    else {
      Iterator<Piece> it = pieces.iterator();
      for(int r = 0; r < rows; r++)  {
        for(int c = 0; c < cols; c++) {
          this.pieces[rows][cols] = it.next();
        }
      }
    }
  }
  
  public Piece getPiece(int row, int col) {
    return pieces[row][col];
  }
}
