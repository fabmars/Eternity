package fr.esiea.glpoo.eternity.dnd;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class MyTransferHandler extends TransferHandler {

  private static final long serialVersionUID = 1L;
  
  
  @Override
  public int getSourceActions(JComponent comp) {
    return MOVE;
  }

  int selectedRow;
  int selectedCol;
  
  
  @Override
  public Transferable createTransferable(JComponent comp) {
    System.out.println("createTransferable");
    
    MyTable table = (MyTable)comp;
    selectedRow = table.getSelectedRow();
    selectedCol = table.getSelectedColumn();
    
    String text = (String) table.getValueAt(selectedRow, selectedCol);
    System.out.println("DND init for: " + text);
    return new StringSelection(text);
  }

  @Override
  public void exportDone(JComponent comp, Transferable trans, int action) {
    System.out.println("exportDone");
    
    if (action == MOVE) {
      MyTable table = (MyTable)comp;
      table.getModel().removeAt(selectedRow, selectedCol);
    }
  }
  
  @Override
  public boolean canImport(TransferSupport support) {
    //System.out.println("canImport");
    return support.isDrop();
  }
  
  @Override
  public boolean importData(TransferSupport support) {
    System.out.println("importData");
    if(canImport(support)) { //to prevent from paste's
      
      DropLocation dl = support.getDropLocation();
      Point dropPoint = dl.getDropPoint();

      String data;
      try {
          data = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
          System.out.println("DND received: " + data);
      } catch (UnsupportedFlavorException | IOException e) {
          return false;
      }

      MyTable table = (MyTable)support.getComponent();
      int row = table.rowAtPoint(dropPoint);
      int col = table.columnAtPoint(dropPoint);

      MyTableModel model = table.getModel();
      Object currentValue = model.getValueAt(row, col);
      if(currentValue == null) { //if there's currently no value on that cell
        model.setValueAt(data, row, col);
        model.fireTableCellUpdated(row, col);
        return true;
      }
    }
    return false;
  }


}
