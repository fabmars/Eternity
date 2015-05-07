package fr.esiea.glpoo.eternity.gui;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleResizeAdapter extends ComponentAdapter {

  private PuzzleTable table;
  
  public PuzzleResizeAdapter(PuzzleTable table) {
    this.table = table;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    super.componentResized(e);

    PuzzleTableModel tm = table.getModel();
    if(tm != null) {
      Puzzle puzzle = tm.getPuzzle();
      if(puzzle != null) {
        Rectangle r = table.getBounds();
        int side = Math.min(r.width/puzzle.getCols(), r.height/puzzle.getRows());
        if(side >= 1) {
          table.setRowHeight(side);
        }
      }
    }
  }
}
