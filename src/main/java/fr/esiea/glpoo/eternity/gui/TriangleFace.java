package fr.esiea.glpoo.eternity.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import fr.esiea.glpoo.eternity.domain.Face;

public class TriangleFace extends JFace {
  private static final long serialVersionUID = 1L;

  public TriangleFace(Face face) {
    super(face);
  }

  @Override
  public void paintPattern(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    
    Polygon triangle = new Polygon();
    triangle.addPoint(width/3, height);
    triangle.addPoint((2*width)/3, height);
    triangle.addPoint((width+1)/2, (2*height)/3);
    g.fillPolygon(triangle);
  
    g.setColor(Color.black);
    g.drawPolygon(triangle);
  }
}
