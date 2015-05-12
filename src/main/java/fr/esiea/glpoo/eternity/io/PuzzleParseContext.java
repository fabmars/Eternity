package fr.esiea.glpoo.eternity.io;

import java.nio.file.Path;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PuzzleParseContext {

  private final Path stateFile;
  
  private Path facesFile;
  private ItemStore<Face> faceStore;
  
  private Path piecesFile;
  private ItemStore<Piece> pieceStore;

  public PuzzleParseContext(Path stateFile) {
    this.stateFile = stateFile;
  }
  
  public Path getStateFile() {
    return stateFile;
  }

  
  public Path getFacesFile() {
    return facesFile;
  }

  protected void setFacesFile(Path faceFile) {
    this.facesFile = faceFile;
  }

  public ItemStore<Face> getFaceStore() {
    return faceStore;
  }

  protected void setFaceStore(ItemStore<Face> faceStore) {
    this.faceStore = faceStore;
  }

  
  public Path getPiecesFile() {
    return piecesFile;
  }

  protected void setPiecesFile(Path pieceFile) {
    this.piecesFile = pieceFile;
  }

  public ItemStore<Piece> getPieceStore() {
    return pieceStore;
  }

  protected void setPieceStore(ItemStore<Piece> pieceStore) {
    this.pieceStore = pieceStore;
  }
}
