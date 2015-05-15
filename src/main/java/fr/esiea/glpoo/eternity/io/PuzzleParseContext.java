package fr.esiea.glpoo.eternity.io;

import java.net.URL;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PuzzleParseContext {

  private final URL stateFile;
  
  private URL facesFile;
  private ItemStore<Face> faceStore;
  
  private URL piecesFile;
  private ItemStore<Piece> pieceStore;

  public PuzzleParseContext(URL stateFile) {
    this.stateFile = stateFile;
  }
  
  public URL getStateFile() {
    return stateFile;
  }

  
  public URL getFacesFile() {
    return facesFile;
  }

  protected void setFacesFile(URL faceFile) {
    this.facesFile = faceFile;
  }

  public ItemStore<Face> getFaceStore() {
    return faceStore;
  }

  protected void setFaceStore(ItemStore<Face> faceStore) {
    this.faceStore = faceStore;
  }

  
  public URL getPiecesFile() {
    return piecesFile;
  }

  protected void setPiecesFile(URL pieceFile) {
    this.piecesFile = pieceFile;
  }

  public ItemStore<Piece> getPieceStore() {
    return pieceStore;
  }

  protected void setPieceStore(ItemStore<Piece> pieceStore) {
    this.pieceStore = pieceStore;
  }
}
