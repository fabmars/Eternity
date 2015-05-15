package fr.esiea.glpoo.eternity.domain;


public class PieceStore extends ItemStore<Piece> {

  @Override
  public void shuffle() {
    super.shuffle();
    for(Piece piece : items) {
      if(piece != null) {
        piece.setOrientation(Orientation.random());
      }
    }
  }
}
