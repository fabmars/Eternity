package fr.esiea.glpoo.eternity.gui;

import static fr.esiea.glpoo.eternity.gui.ImageUtils.COLOR_MODEL_RGBA;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.Piece;

/**
 * That would be muuuch easier to just trace the faces from within paint() here but I wanted to us a Caontainer
 */
public class JPiece extends Container {

  private static final long serialVersionUID = 1L;

  public JPiece(Piece piece) {
    if(piece != null) {
      for(Face face : piece) {
        JFace jface = JFaceFactory.create(face);
        add(jface);
      }
    }
  }

  /**
   * Trying to make sure the component will be square
   */
  @Override
  public void setPreferredSize(Dimension preferredSize) {
    int min = Math.min(preferredSize.width, preferredSize.height);
    super.setPreferredSize(new Dimension(min, min));
  }
  
  @Override
  public void paint(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Point center = new Point((width+1)/2, (height+1)/2);

    //BufferedImage pImage = new BufferedImage(width, height, getScreenImageType());
    //Graphics2D pGraph = pImage.createGraphics();
    Graphics2D pGraph = (Graphics2D)g; //instead of creating yet another image above, we're assuming g is a G2D, which it is :)
        
    pGraph.translate(center.x, center.y); //defining new origin at the center of the piece
    pGraph.rotate(Math.PI); //would also work using an AffineTransform rotated by 2 quadrants around center

    Dimension fDim = new Dimension(width, center.y);
    for(Component jFace : getComponents()) {
      jFace.setSize(fDim);
      //creating temp working image with same image type as the screen's
      BufferedImage faceImage = ImageUtils.createBufferedImage(COLOR_MODEL_RGBA, fDim); //we want it with Alpha so the quadrants' "empty" parts don't overlap on their neighbors
      jFace.paint(faceImage.createGraphics());
      pGraph.drawImage(faceImage, -center.x, 0, this);

      pGraph.rotate(Math.PI/2.0); //would also work using an AffineTransform rotated by 1 quadrant around center
    }
    
    //g.drawImage(pImage, 0, 0, this);
  }
}
