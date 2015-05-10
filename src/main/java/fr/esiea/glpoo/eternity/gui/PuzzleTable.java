package fr.esiea.glpoo.eternity.gui;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleTable extends JTable {

  private static final long serialVersionUID = 1L;

  public PuzzleTable() {
    this( new PuzzleTableModel());
  }

  public PuzzleTable(Puzzle puzzle) {
    this( new PuzzleTableModel(puzzle));
  }
  
  public PuzzleTable(PuzzleTableModel tableModel) {
    super(tableModel);
    setDefaultRenderer(Piece.class, new PieceCellRenderer());

    setBackground(Color.darkGray);
    
    addComponentListener(new PuzzleResizeAdapter(this));
    
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    addMouseListener(new PuzzleMouseAdapter(this));
  }
  
  @Override
  public PuzzleTableModel getModel() {
    return (PuzzleTableModel)super.getModel();
  }

  @Override
  public Piece getValueAt(int row, int column) {
    return (Piece)super.getValueAt(row, column);
  }

  @Override
  public void paint(Graphics g) {
    //paint pieces
    super.paint(g);
    
    //highlight selection 
    int col = getSelectedColumn();
    int row = getSelectedRow();
    
    if(col >=0 && row >= 0 && getValueAt(row, col) != null) { //some piece is selected
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(Color.cyan);
      int tableDim = min(getWidth()/getColumnCount(), getHeight()/getRowCount());
      float stroke = max(1.f, min(2.f, tableDim/60.f));
      g2d.setStroke(new BasicStroke(stroke));

      Rectangle r = getCellRect(row, col, false);
      g2d.drawRect(r.x, r.y, r.width, r.height);
    }
  }
}
