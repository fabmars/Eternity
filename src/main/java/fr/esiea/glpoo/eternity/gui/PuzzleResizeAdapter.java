package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PuzzleResizeAdapter extends ComponentAdapter {

  private PuzzleTable table;
  
  public PuzzleResizeAdapter(PuzzleTable table) {
    this.table = table;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    table.resetRowHeight();
  }
}
