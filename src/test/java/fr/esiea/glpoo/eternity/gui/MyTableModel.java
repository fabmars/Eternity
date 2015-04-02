package fr.esiea.glpoo.eternity.gui;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;
  
  private String[] dataSource = new String[16];

  public MyTableModel(boolean fill) {
    if(fill) {
      for(int i = 0; i < dataSource.length; i++) {
        dataSource[i] = "Label " + i;
      }
    }
  }
  
  @Override
  public int getRowCount() {
    return 4;
  }

  @Override
  public int getColumnCount() {
    return 4;
  }

  @Override
  public Object getValueAt(int row, int col) {
    int index = getIndex(row, col);
    return dataSource[index];
  }

  @Override
  public void setValueAt(Object aValue, int row, int col) {
    int index = getIndex(row, col);
    dataSource[index] = (String)aValue;
  }

  @Override
  public Class<?> getColumnClass(int col) {
    return String.class;
  }
  
  public void removeAt(int row, int col) {
    int index = getIndex(row, col);
    dataSource[index] = null;
    fireTableCellUpdated(row, col);
  }
  
  private int getIndex(int row, int col) {
    return row*4 + col;
  }
}
