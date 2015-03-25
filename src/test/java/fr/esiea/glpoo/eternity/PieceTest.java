package fr.esiea.glpoo.eternity;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Test;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceType;
import fr.esiea.glpoo.eternity.domain.Pattern;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceTest {
  Face f1a = new Face(1, FaceType.FACE, Color.red, Pattern.LINES, Color.blue);
  Face f1b = new Face(2, FaceType.FACE, Color.red, Pattern.LINES, Color.blue);
  Face f2 = new Face(3, FaceType.EDGE, Color.yellow, Pattern.TRIANGLE, Color.gray);
  Face f3 = new Face(4, FaceType.EDGE, Color.green, Pattern.ZIGZAG, Color.white);
  Face f4 = new Face(5, FaceType.EDGE, Color.black, Pattern.TRIANGLE, Color.cyan);
  Piece p1a = new Piece(1, f1a, f2, f3, f4);
  Piece p1b = new Piece(3, f4, f1a, f2, f3); //p1a rotated
  Piece p2 = new Piece(2, f2, f1a, f3, f4);
  
  @Test
  public void testFaceEquals() {
    Assert.assertSame(f1a, f1b);
  }

  @Test
  public void testFaceDifferent() {
    Assert.assertSame(f1a, f2);
  }
  
  @Test
  public void testPieceDifferent() {
    Assert.assertNotSame(p1a, p2);
  }
  
  @Test
  public void testPieceRotated() {
    Assert.assertSame(p1a, p1b);
  }
  
  
}
