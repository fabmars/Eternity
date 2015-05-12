package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleParseReport extends CsvParseReport<Puzzle> {

  private ItemStore<Piece> pieces;

  public PuzzleParseReport() {
    super();
  }

  public PuzzleParseReport(int errorLimit) {
    super(errorLimit);
  }

  public ItemStore<Piece> getPieces() {
    return pieces;
  }

  public void setPieces(ItemStore<Piece> pieces) {
    this.pieces = pieces;
  }
}
