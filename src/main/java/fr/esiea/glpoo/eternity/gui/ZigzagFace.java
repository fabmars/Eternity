package fr.esiea.glpoo.eternity.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import fr.esiea.glpoo.eternity.domain.Face;

public class ZigzagFace extends JFace {
  private static final long serialVersionUID = 1L;

  public ZigzagFace(Face face) {
    super(face);
  }

  @Override
  public void paintPattern(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Graphics2D g2d = (Graphics2D)g;

    int x1 = width/3;
    int x2 = (5*width)/12;
    int x3 = (7*width)/12;
    int x4 = (2*width)/3;
    int hstep = height/6;
    
    Polygon zigzag = new Polygon();
    int h = height;
    while(h > 0) {
      zigzag.addPoint(x2, h);
      h -= hstep;
      zigzag.addPoint(x1, h);
      h -= hstep;
    }
    while(h <= height) {
      zigzag.addPoint(x3, h);
      h += hstep;
      zigzag.addPoint(x4, h);
      h += hstep;
    }
    g2d.fillPolygon(zigzag);

    //stroke
    g2d.setColor(getStrokeColor());
    g2d.setStroke(new BasicStroke(getStrokeWidth()));
    g2d.drawPolygon(zigzag);
  }
}
