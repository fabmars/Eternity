package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceDao extends GenericDao<Piece> {

  private ItemStore<Face> faceStore;
  private ItemStore<Piece> store = new ItemStore<Piece>();
  

  public PieceDao(ItemStore<Face> faceStore) {
    this.faceStore = faceStore;
  }

  @Override
  public Piece parseLine(String[] parts) throws CsvException {
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
    
    Piece piece = new Piece(id, northFace, westFace, southFace, eastFace);
    return piece;
  }

  @Override
  public void insert(Piece piece) {
    store.add(piece);
  }

  public ItemStore<Piece> getPieces() {
    return store;
  }
}
