package fr.esiea.glpoo.eternity.gui;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.Pattern;

public class JFaceFactory {

  public static JFace create(Face face) {
    Pattern pat = face.getPattern();
    if(pat != null) {
      switch (pat) {
      case TRIANGLE:
        return new TriangleFace(face);
  
      case ZIGZAG:
        return new ZigzagFace(face);
  
      case CROWN:
        return new CrownFace(face);
  
      case LINES:
        return new LinesFace(face);
        
      default:
        return null; //will end up on a NPE in the short run
      }
    }
    else {
      return new PatternlessFace(face);
    }
  }
}
