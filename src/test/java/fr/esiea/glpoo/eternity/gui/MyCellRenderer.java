package fr.esiea.glpoo.eternity.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Pointless
 */
public class MyCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    if((row * 4 + column)%2 == 0) {
      comp.setBackground(Color.orange);
    }
    else {
      comp.setBackground(Color.cyan);
    }
    
    return comp;
  }
}
