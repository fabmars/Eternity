package fr.esiea.glpoo.eternity.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import fr.esiea.glpoo.eternity.domain.Face;

public class CrownFace extends JFace {
  private static final long serialVersionUID = 1L;

  public CrownFace(Face face) {
    super(face);
  }

  @Override
  public void paintPattern(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Graphics2D g2d = (Graphics2D)g;
    
    int x1 = (5*width)/12;
    int x2 = width/3;
    int x4 = (width+1)/2;
    int x6 = (2*width)/3;
    int x5 = (7*width)/12;
    
    int h2 = (3*height)/4;
    int h4 = height/2;
    
    Polygon crown = new Polygon();
    crown.addPoint(x1, height);
    crown.addPoint(x2, h2);
    crown.addPoint(x1, h2);
    crown.addPoint(x4, h4);
    crown.addPoint(x5, h2);
    crown.addPoint(x6, h2);
    crown.addPoint(x5, height);
    g2d.fillPolygon(crown);

    //stroke
    g2d.setColor(getStrokeColor());
    g2d.setStroke(new BasicStroke(getStrokeWidth()));
    g2d.drawPolygon(crown);
  }
}
