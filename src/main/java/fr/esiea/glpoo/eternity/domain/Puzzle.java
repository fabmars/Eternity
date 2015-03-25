package fr.esiea.glpoo.eternity.domain;

public class Puzzle {

  private Piece[][] pieces;
  private int rows, cols;
  
  public Puzzle(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.pieces = new Piece[rows][cols];
  }
  
  public void setPieces(Piece[] pieces) {
    if(pieces.length != rows*cols) {
      throw new IllegalArgumentException("Bad pieces list size (" + pieces.length + ") for puzzle[" + rows + "][" + cols +"]");
    }
    else {
      int i = 0;
      for(int r = 0; r < rows; i++)  {
        for(int c = 0; c < cols; c++) {
          this.pieces[rows][cols] = pieces[i++];
        }
      }
    }
  }
  
  public Piece getPiece(int row, int col) {
    return pieces[row][col];
  }
}
