package fr.esiea.glpoo.eternity.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import fr.esiea.glpoo.eternity.domain.Face;

public class LinesFace extends JFace {
  private static final long serialVersionUID = 1L;

  public LinesFace(Face face) {
    super(face);
  }

  @Override
  public void paintPattern(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Graphics2D g2d = (Graphics2D)g;
    
    int lw = width/12;
    int x1 = width/3;
    int l1x1 = x1 - lw;
    int l1x2 = x1 + lw;
    int x2 = (2*width)/3;
    int l2x1 = x2 - lw;
    int l2x2 = x2 + lw;
    
    
    Polygon line1 = new Polygon();
    line1.addPoint(l1x1, height);
    line1.addPoint(l1x1, 0);
    line1.addPoint(l1x2, 0);
    line1.addPoint(l1x2, height);
    g.fillPolygon(line1);

    Polygon line2 = new Polygon();
    line2.addPoint(l2x1, height);
    line2.addPoint(l2x1, 0);
    line2.addPoint(l2x2, 0);
    line2.addPoint(l2x2, height);
    g.fillPolygon(line2);
    
    //stroke
    g2d.setColor(getStrokeColor());
    g2d.setStroke(new BasicStroke(getStrokeWidth()));
    g2d.drawLine(l1x1, height, l1x1, 0);
    g2d.drawLine(l1x2, height, l1x2, 0);
    g2d.drawLine(l2x1, height, l2x1, 0);
    g2d.drawLine(l2x2, height, l2x2, 0);
  }
}
