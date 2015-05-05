package fr.esiea.glpoo.eternity.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
    g2d.setStroke(new BasicStroke(width/12.f));
    
    int x1 = width/3;
    int x2 = (2*width)/3;
    
    g2d.drawLine(x1, 0, x1, height);
    g2d.drawLine(x2, 0, x2, height);
  }
}
