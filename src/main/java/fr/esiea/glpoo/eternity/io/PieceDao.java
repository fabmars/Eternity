package fr.esiea.glpoo.eternity.io;

import java.util.LinkedList;
import java.util.List;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceStore;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceDao extends GenericDao<Piece> {

  private FaceStore faceStore;
  private List<Piece> pieces = new LinkedList<>();
  

  public PieceDao(FaceStore faceStore) {
    this.faceStore = faceStore;
  }

  @Override
  public Piece parseLine(String[] parts) throws CsvException {
    int i = 1; //skipping first P
    int id = Integer.parseInt(parts[i++].trim());
    int fidNorth = Integer.parseInt(parts[i++]);
    int fidEast = Integer.parseInt(parts[i++]);
    int fidSouth = Integer.parseInt(parts[i++]);
    int fidWest = Integer.parseInt(parts[i++]);
    
    Face northFace = faceStore.get(fidNorth);
    Face eastFace = faceStore.get(fidEast);
    Face southFace = faceStore.get(fidSouth);
    Face westFace = faceStore.get(fidWest);
    
    Piece piece = new Piece(id, northFace, westFace, southFace, eastFace);
    return piece;
  }

  @Override
  public void insert(Piece piece) {
    pieces.add(piece);
  }

  public List<Piece> getPieces() {
    return pieces;
  }
}
