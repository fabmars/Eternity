package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.esiea.glpoo.eternity.domain.Piece;

public class RotateActionListener implements ActionListener {

  private PuzzleTable table;
  
  public RotateActionListener(PuzzleTable table) {
    this.table = table;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    
    if(col >= 0 && row >= 0) { //some cell is selected
      Piece piece = table.getValueAt(row, col);
      if(piece != null) { //there is some piece in the cdell
        piece.rotateClockwise();
        table.getModel().fireTableCellUpdated(row, col);
      }
    }
  }
}
