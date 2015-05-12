package fr.esiea.glpoo.eternity.io;

import java.awt.Color;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceType;
import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Pattern;

public class FaceDao extends GenericDao<Face, Void, ItemStore<Face>> {

  private final static FrenchColorAdapter colorAdapter = new FrenchColorAdapter();
  
  @Override
  public Face parseLine(Void context, String[] parts) throws CsvException {
    try {
      int i = 0;
      FaceType type = FaceType.getByCode(parts[i++].trim());
      int id = Integer.parseInt(parts[i++].trim());
      Color backColor =  colorAdapter.getAsObject(parts[i++].trim());
      Pattern pattern = null;
      Color patternColor = null;
      if(type == FaceType.FACE) {
        pattern = Pattern.getByCode(parts[i++].trim());
        patternColor =  colorAdapter.getAsObject(parts[i++].trim());
      }      
      return new Face(id, type, backColor, pattern, patternColor);
    }
    catch(EnumConstantNotPresentException e) {
      throw new CsvException(e);
    }
  }
  
  @Override
  public ItemStore<Face> createOutcome(Void context) {
    return new ItemStore<>();
  }

  @Override
  public void insert(ItemStore<Face> faceStore, Face face) {
    faceStore.add(face);
  }
}
