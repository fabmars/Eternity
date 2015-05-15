package fr.esiea.glpoo.eternity.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.CellRendererPane;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceTransferHandler extends TransferHandler {

  private static final long serialVersionUID = 1L;
  private final static JPanel dummyPanel = new JPanel();
  private final static CellRendererPane crp = new CellRendererPane();
  

  
  public PieceTransferHandler(JTable tableSource, JTable tableDest) {
    super();
    
    tableSource.setDragEnabled(true);
    tableSource.setTransferHandler(this);
    tableSource.setDropMode(DropMode.ON);

    tableDest.setDragEnabled(true);
    tableDest.setTransferHandler(this);
    tableDest.setDropMode(DropMode.ON);
  }

  @Override
  public int getSourceActions(JComponent comp) {
    /**
     * Workaround to this nasty bug:
     * @see http://bugs.java.com/view_bug.do?bug_id=4816922
     */
    PuzzleTable table = (PuzzleTable) comp;
    Image dragImage = buildSelectedCellImage(table);
    setDragImage(dragImage);
    
    return MOVE;
  }

  private int selectedRow;
  private int selectedCol;
  
  @Override
  public Transferable createTransferable(JComponent comp) {
    PuzzleTable table = (PuzzleTable) comp;
    selectedRow = table.getSelectedRow();
    selectedCol = table.getSelectedColumn();
    
    if(selectedRow >= 0 && selectedCol >= 0) {
      Piece piece = table.getValueAt(selectedRow, selectedCol);
      return (piece != null) ? new PieceSelection(piece) : null;
    }
    else {
      return null;
    }
  }

  @Override
  public void exportDone(JComponent comp, Transferable trans, int action) {
    if (action == MOVE) {
      PuzzleTable table = (PuzzleTable)comp;
      table.getModel().removeAt(selectedRow, selectedCol);
    }
  }
  
  @Override
  public boolean canImport(TransferSupport support) {
    return support.isDrop();
  }
  
  @Override
  public boolean importData(TransferSupport support) {
    if(canImport(support)) { //to prevent from paste's
      
      DropLocation dl = support.getDropLocation();
      Point dropPoint = dl.getDropPoint();

      Piece piece;
      try {
          piece = (Piece)support.getTransferable().getTransferData(PieceSelection.flavor);
      } catch (UnsupportedFlavorException | IOException e) {
          return false;
      }

      PuzzleTable table = (PuzzleTable)support.getComponent();
      int row = table.rowAtPoint(dropPoint);
      int col = table.columnAtPoint(dropPoint);

      PuzzleTableModel model = table.getModel();
      Object currentValue = model.getValueAt(row, col);
      if(currentValue == null) { //if there's currently no value on that cell
        model.setValueAt(piece, row, col); //applying the piece into that cell
        model.fireTableCellUpdated(row, col); //forcing redraw
        
        //getting the previously selected cell
        int selectedRow = table.getSelectedRow();
        int selectedCol = table.getSelectedColumn();
        //making the dropped piece the selected one
        table.changeSelection(row, col, false, false);
        if(selectedRow >=0 && selectedCol >= 0) { //if some cell was previously selected, it has to be redrawn too
          model.fireTableCellUpdated(selectedRow, selectedCol);
        }
        
        table.checkSolved();
        return true;
      }
    }
    return false;
  }


  /**
   * Never called as per long standing bug
   * @see http://bugs.java.com/view_bug.do?bug_id=4816922
   */
  @Override
  public Icon getVisualRepresentation(Transferable t) {
    try {
      Piece piece = ((Piece)t.getTransferData(PieceSelection.flavor));

      Image dragimage = buildPieceImage(piece, new Dimension(32, 32));
      return new ImageIcon(dragimage);
    }
    catch (UnsupportedFlavorException | IOException e) {
      return null; //won't get any visual representation... 
    }
  }

  
  private Image buildSelectedCellImage(PuzzleTable table) {
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();

    if(row >= 0 && col >= 0) {
      Piece piece = table.getValueAt(row, col);
      Dimension dim = table.getCellRect(row, col, false).getSize();
      return buildPieceImage(piece, dim);
    }
    else {
      return null;
    }
  }

  private Image buildPieceImage(Piece piece, Dimension dim) {
    BufferedImage image = null;
    if(piece != null && dim != null) {
      image = new BufferedImage(dim.width,  dim.height,  BufferedImage.TYPE_INT_RGB);
      JPiece jPiece = new JPiece(piece);
      crp.paintComponent(image.getGraphics(), jPiece, dummyPanel, 0, 0, dim.width, dim.height); //SwingUtilities#paintComponent would work too.
    }
    return image;
  }

}
