package fr.esiea.glpoo.eternity.io;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FrenchColorAdapter implements Adapter<Color> {
  
  private final static HashMap<String, Color> colorMap = new HashMap<>();
  
  static {
    colorMap.put("noir", Color.black);
    colorMap.put("bleu", Color.blue);
    colorMap.put("cyan", Color.cyan);
    colorMap.put("gris", Color.gray);
    colorMap.put("vert", Color.green);
    colorMap.put("magenta", Color.magenta);
    colorMap.put("orange", Color.orange);
    colorMap.put("rose", Color.pink);
    colorMap.put("rouge", Color.red);
    colorMap.put("blanc", Color.white);
    colorMap.put("jaune", Color.yellow);
  }
  
  
  @Override
  public Color getAsObject(String str) throws CsvException {
    Color color = colorMap.get(str);
    if(color != null) {
      return color;
    }
    else {
      throw new IllegalArgumentException("Unknown color: " + str);
    }
  }

  
  @Override
  public String getAsString(Color color) throws CsvException {
    String result = null;
    for(Map.Entry<String, Color> ce : colorMap.entrySet()) {
      if(Objects.equals(ce.getValue(), color)) {
        result = ce.getKey();
        break;
      }
    }
    return result;
  }
}
