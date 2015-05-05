package fr.esiea.glpoo.eternity.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.JComponent;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.Pattern;
import fr.esiea.glpoo.eternity.domain.Piece;

public class JPiece extends JComponent {

  private static final long serialVersionUID = 1L;
  
  private Piece piece;

  public JPiece(Piece piece) {
    this.piece = piece;
  }

  public Piece getPiece() {
    return piece;
  }


  @Override
  public void paint(Graphics g) {
    if(piece != null) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
      ColorModel cm = gc.getColorModel();
      int imageType = cm.getColorSpace().getType();
      
      //creating temp working image with same image type as the screen's
      int width = getWidth();
      int height = getHeight();
      BufferedImage image = new BufferedImage(width, height, imageType);

      //creating a Face shape 
      Point center = new Point((width+1)/2, (height+1)/2);
      Polygon triangle = new Polygon();
      triangle.addPoint(0, height);
      triangle.addPoint(width, height);
      triangle.addPoint(center.x, center.y);

      
      Graphics2D g2d = ge.createGraphics(image); //OK that would probably work by casting g into a G2D, but we can't be sure.
      AffineTransform at = new AffineTransform();
      at.quadrantRotate(2, center.x, center.y); //pieces were drawn as though they're laid at the south, so we have to start with a π-rotation 
      g2d.setTransform(at);
      
      for(Face face : piece) { //takes orientation into account
        //fill face background
        g2d.setColor(face.getBackgroundColor());
        g2d.fillPolygon(triangle);

        if(face.isFace()) {
          //trace pattern
          g2d.setColor(face.getPatternColor());
          Pattern pat = face.getPattern();
          String s;
          switch(pat) {
          case CROWN:
            s = "K";
            break;
          case LINES:
            s = "L";
            break;
          case TRIANGLE:
            s = "T";
            break;
          case ZIGZAG:
            s = "Z";
            break;
          default:
            s = "X";
            break;
          }
          g2d.drawString(s, width/2, (3*height)/4);
        }
        //else is unlikely
        
        at.quadrantRotate(1, center.x, center.y); //rotating clockwise, that is, in the same order as in the Orientation enum
        g2d.setTransform(at);
      }
      
      g.drawImage(image, 0, 0, this);
    }
  }
}
