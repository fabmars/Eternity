package fr.esiea.glpoo.eternity.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    
    Graphics2D g2d = (Graphics2D)g;

    int x1 = width/3;
    int x2 = (width+1)/2;
    int x3 = (2*width)/3;
    int h2 = (2*height)/3;
    
    Polygon triangle = new Polygon();
    triangle.addPoint(x1, height);
    triangle.addPoint(x2, h2);
    triangle.addPoint(x3, height);
    g.fillPolygon(triangle);
  
    //stroke
    g2d.setColor(getStrokeColor());
    g2d.setStroke(new BasicStroke(getStrokeWidth()));
    g2d.drawLine(x1, height, x2, h2);
    g2d.drawLine(x2, h2, x3, height);
  }
}
