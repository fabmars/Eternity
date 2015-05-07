package fr.esiea.glpoo.eternity.gui;

import javax.swing.table.AbstractTableModel;

import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;
  
  private Puzzle puzzle;

  public PuzzleTableModel() {
    //nothing
  }

  public PuzzleTableModel(Puzzle puzzle) {
    setPuzzle(puzzle);
  }
  
  
  public Puzzle getPuzzle() {
    return puzzle;
  }
  
  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;
  }
  
  
  @Override
  public int getRowCount() {
    return (puzzle != null) ? puzzle.getRows() : 0;
  }

  @Override
  public int getColumnCount() {
    return (puzzle != null) ? puzzle.getCols() : 0;
  }

  @Override
  public Piece getValueAt(int row, int col) {
    return puzzle.getPiece(row, col);
  }

  @Override
  public void setValueAt(Object aValue, int row, int col) {
    Piece piece = (Piece)aValue;
    puzzle.setPiece(piece, row, col);
  }

  @Override
  public Class<?> getColumnClass(int col) {
    return Piece.class;
  }
  
  public void removeAt(int row, int col) {
    puzzle.setPiece(null, row, col);
    fireTableCellUpdated(row, col);
  }
}
