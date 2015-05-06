package fr.esiea.glpoo.eternity.io;

import java.awt.Color;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceType;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Pattern;

public class FaceDao extends GenericDao<Face> {

  private final ItemStore<Face> faceStore;
  private final static FrenchColorAdapter colorAdapter = new FrenchColorAdapter();
  
  public FaceDao(ItemStore<Face> faceStore) {
    this.faceStore = faceStore;
  }

  @Override
  public Face parseLine(String[] parts) throws CsvException {
    try {
      int i = 0;
      FaceType type = FaceType.getByCode(parts[i++].trim());
      int id = Integer.parseInt(parts[i++].trim());
      Color backColor =  colorAdapter.getAsObject(parts[i++].trim());
      Pattern pattern = Pattern.getByCode(parts[i++].trim());
      Color patternColor =  colorAdapter.getAsObject(parts[i++].trim());
      
      return new Face(id, type, backColor, pattern, patternColor);
    }
    catch(EnumConstantNotPresentException e) {
      throw new CsvException(e);
    }
  }

  @Override
  public void insert(Face face) {
    faceStore.add(face);
  }
}
