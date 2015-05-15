package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.esiea.glpoo.eternity.domain.Piece;

public class RotateActionListener extends MouseAdapter implements ActionListener {

  private PuzzleTable table;
  private SolutionHandler solutionHandler;
  
  public RotateActionListener(PuzzleTable table, SolutionHandler solutionHandler) {
    this.table = table;
    this.solutionHandler = solutionHandler;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    rotateSelectedPiece();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(e.getClickCount() == 2) { //double click
      rotateSelectedPiece();
    }
  }
  
  public void rotateSelectedPiece() {
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    rotatePiece(row, col);
  }

  public void rotatePiece(int row, int col) {
    if(col >= 0 && row >= 0) { //some cell is selected
      Piece piece = table.getValueAt(row, col);
      if(piece != null) { //there is some piece in the cdell
        piece.rotateClockwise();
        table.getModel().fireTableCellUpdated(row, col);
        
        if(solutionHandler != null) {
          solutionHandler.checkSolved(table.getModel().getPuzzle());
        }
      }
    }
  }
}
