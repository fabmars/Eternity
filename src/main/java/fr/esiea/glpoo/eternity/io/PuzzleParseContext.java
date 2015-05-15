package fr.esiea.glpoo.eternity.io;

import java.net.URL;

import fr.esiea.glpoo.eternity.domain.FaceStore;
import fr.esiea.glpoo.eternity.domain.PieceStore;

public class PuzzleParseContext {

  private final URL stateFile;
  
  private URL facesFile;
  private FaceStore faceStore;
  
  private URL piecesFile;
  private PieceStore pieceStore;

  public PuzzleParseContext(URL stateFile) {
    this.stateFile = stateFile;
  }
  
  public PuzzleParseContext(URL facesFile, URL piecesFile) {
    this(null);
    setFacesFile(facesFile);
    setPiecesFile(piecesFile);
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

  public FaceStore getFaceStore() {
    return faceStore;
  }

  protected void setFaceStore(FaceStore faceStore) {
    this.faceStore = faceStore;
  }

  
  public URL getPiecesFile() {
    return piecesFile;
  }

  protected void setPiecesFile(URL pieceFile) {
    this.piecesFile = pieceFile;
  }

  public PieceStore getPieceStore() {
    return pieceStore;
  }

  protected void setPieceStore(PieceStore pieceStore) {
    this.pieceStore = pieceStore;
  }
}
