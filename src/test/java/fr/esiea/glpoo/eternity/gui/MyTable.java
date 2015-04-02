package fr.esiea.glpoo.eternity.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class MyTable extends JTable {

  private static final long serialVersionUID = 1L;
  
  public MyTable(boolean withData) {
    this( new MyTableModel(withData));
  }
  
  public MyTable(MyTableModel tableModel) {
    super(tableModel);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  
  @Override
  public MyTableModel getModel() {
    return (MyTableModel)super.getModel();
  }
}
