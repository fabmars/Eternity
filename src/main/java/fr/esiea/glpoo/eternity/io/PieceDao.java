package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceStore;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.PieceStore;

public class PieceDao extends GenericDao<Piece, FaceStore, PieceStore> {

  @Override
  public PieceStore createOutcome(FaceStore context) {
    return new PieceStore();
  }

  @Override
  public Piece parseLine(FaceStore faceStore, String[] parts) throws CsvException {
    int i = 1; //skipping first P
    int id = Integer.parseInt(parts[i++].trim());
    int idNorth = Integer.parseInt(parts[i++]);
    int idEast = Integer.parseInt(parts[i++]);
    int idSouth = Integer.parseInt(parts[i++]);
    int idWest = Integer.parseInt(parts[i++]);
    
    Face northFace = faceStore.get(idNorth);
    Face eastFace = faceStore.get(idEast);
    Face southFace = faceStore.get(idSouth);
    Face westFace = faceStore.get(idWest);
    
    Piece piece = new Piece(id, northFace, eastFace, southFace, westFace);
    return piece;
  }

  @Override
  public void insert(PieceStore outcome, Piece piece) {
    outcome.add(piece);
  }
}
