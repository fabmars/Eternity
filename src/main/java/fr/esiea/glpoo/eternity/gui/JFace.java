package fr.esiea.glpoo.eternity.gui;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;

import fr.esiea.glpoo.eternity.domain.Face;

public abstract class JFace extends JComponent {

  private static final long serialVersionUID = 1L;
  
  protected final Face face;
  
  protected JFace(Face face) {
    this.face = face;
  }

  @Override
  public void paint(Graphics g) {
    paintBackground(g);
    if(face.isFace()) {
      g.setColor(face.getPatternColor());
      g.setClip(getClip());
      paintPattern(g);
    }
  }

  private void paintBackground(Graphics g) {
    g.setColor(face.getBackgroundColor());
    Polygon backShape = getClip();
    g.fillPolygon(backShape);
  }
  
  public abstract void paintPattern(Graphics g);

  
  protected Polygon getClip() {
    int width = getWidth();
    int height = getHeight();

    Polygon triangle = new Polygon();
    triangle.addPoint(0, height);
    triangle.addPoint(width, height);
    triangle.addPoint((width+1)/2, 0);
    
    return triangle;
  }
  
}
