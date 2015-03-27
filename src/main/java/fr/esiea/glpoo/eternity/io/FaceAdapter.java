package fr.esiea.glpoo.eternity.io;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceStore;


/**
 * Can't be an AdapterCsv, we need to ba able to fetch from the FaceStore,
 * and I wouldn't use a singleton
 */
public class FaceAdapter implements Adapter<Face> {

  private FaceStore faceStore;
  
  public FaceAdapter(FaceStore faceStore) {
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
