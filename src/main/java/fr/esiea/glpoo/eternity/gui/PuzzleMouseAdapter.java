package fr.esiea.glpoo.eternity.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PuzzleMouseAdapter extends MouseAdapter {

  private PuzzleTable table;
  
  public PuzzleMouseAdapter(PuzzleTable table) {
    super();
    this.table = table;
  }

  
  @Override
  public void mouseClicked(MouseEvent e) {
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    System.out.println("CLIQUE: " + row + ", " + col);
  }
  
  
}
