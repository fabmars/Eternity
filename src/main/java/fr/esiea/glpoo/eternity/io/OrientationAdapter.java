package fr.esiea.glpoo.eternity.io;

import static fr.esiea.glpoo.eternity.domain.Orientation.EAST;
import static fr.esiea.glpoo.eternity.domain.Orientation.NORTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.SOUTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.WEST;
import fr.esiea.glpoo.eternity.domain.Orientation;

public class OrientationAdapter implements Adapter<Orientation> {

  private final static String N = "N";
  private final static String E = "E";
  private final static String S = "S";
  private final static String W = "W";
  
  @Override
  public Orientation getAsObject(String s) throws CsvException {
    switch(s) {
    case N:
      return NORTH;
    
    case E:
      return EAST;
      
    case S:
      return SOUTH;
        
    case W:
      return WEST;
      
      default:
        throw new IllegalArgumentException(String.valueOf(s));
    }
  }

  @Override
  public String getAsString(Orientation object) throws CsvException {
    switch(object) {
    case NORTH:
      return N;
    
    case EAST:
      return E;
      
    case SOUTH:
      return S;
        
    case WEST:
      return W;
      
    default:
      throw new IllegalArgumentException(String.valueOf(object));
    }
  }
}
