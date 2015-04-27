package fr.esiea.glpoo.eternity.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import fr.esiea.glpoo.eternity.domain.Piece;

/**
 * DND component managing the transfer of a piece
 */
public class PieceSelection implements Transferable {

  public final static DataFlavor flavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, JPiece.class.getSimpleName());
  private final static DataFlavor[] flavorAsArray = { flavor };
  
  private Piece piece;
  
  public PieceSelection(Piece piece) {
    this.piece = piece;
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return flavorAsArray;
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return PieceSelection.flavor.equals(flavor);
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    return isDataFlavorSupported(flavor) ? piece : null;
  }
}
