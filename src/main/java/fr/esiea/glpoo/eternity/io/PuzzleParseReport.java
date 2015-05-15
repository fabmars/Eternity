package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.PieceStore;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleParseReport extends CsvParseReport<Puzzle> {

  private PieceStore pieces;

  public PuzzleParseReport() {
    super();
  }

  public PuzzleParseReport(int errorLimit) {
    super(errorLimit);
  }

  public PieceStore getPieces() {
    return pieces;
  }

  public void setPieces(PieceStore pieces) {
    this.pieces = pieces;
  }
}
