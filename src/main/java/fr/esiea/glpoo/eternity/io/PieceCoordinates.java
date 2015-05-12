package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Piece;

class PieceCoordinates {

  protected final Piece piece;
  protected final int x;
  protected final int y;
  
  
  public PieceCoordinates(Piece piece, int x, int y) {
    this.piece = piece;
    this.x = x;
    this.y = y;
  }
}
