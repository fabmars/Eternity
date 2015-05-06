package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.ItemStore;


/**
 * Can't be an AdapterCsv, we need to ba able to fetch from the ItemStore<Face>,
 * and I wouldn't use a singleton
 */
@Deprecated
public class FaceAdapter implements Adapter<Face> {

  private ItemStore<Face> faceStore;
  
  public FaceAdapter(ItemStore<Face> faceStore) {
    this.faceStore = faceStore;
  }
  
  @Override
  public Face getAsObject(String sId) throws CsvException {
    try {
      int id = Integer.parseInt(sId);
      Face face = faceStore.get(id);
      if(face == null) {
        throw new CsvException("Can't find Face with id: " + sId);
      }
      return face;
    }
    catch(NumberFormatException e) {
      throw new CsvException(e);
    }
  }
  
  @Override
  public String getAsString(Face face) throws CsvException {
    return Integer.toString(face.getId());
  }
}
