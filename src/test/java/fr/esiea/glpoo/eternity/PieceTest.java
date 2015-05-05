package fr.esiea.glpoo.eternity;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.junit.Test;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceType;
import fr.esiea.glpoo.eternity.domain.Orientation;
import fr.esiea.glpoo.eternity.domain.Pattern;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceTest {
  Face f1a = new Face(1, FaceType.FACE, Color.red, Pattern.LINES, Color.blue);
  Face f1b = new Face(2, FaceType.FACE, Color.red, Pattern.LINES, Color.blue);
  Face f2 = new Face(3, FaceType.EDGE, Color.yellow, Pattern.TRIANGLE, Color.gray);
  Face f3 = new Face(4, FaceType.EDGE, Color.green, Pattern.ZIGZAG, Color.white);
  Face f4 = new Face(5, FaceType.EDGE, Color.black, Pattern.TRIANGLE, Color.cyan);
  
  Piece p1a = new Piece(1, f1a, f2, f3, f4, Orientation.SOUTH);
  Piece p1b = new Piece(p1a).rotateClockwise(); //p1a rotated via orientation
  Piece p1c = new Piece(2, f3, f4, f1a, f2, Orientation.WEST); //p1a rotated via array AND orientation
  Piece p2 = new Piece(3, f2, f1a, f3, f4, Orientation.SOUTH);
  
  @Test
  public void testFaceSame() {
    assertEquals(f1a, f1b);
  }

  @Test
  public void testFaceDifferent() {
    assertThat(f1a, not(equalTo(f2)));
  }
  
  @Test
  public void testPieceRotated1IsSame() {
    assertEquals(p1a, p1b);
  }

  @Test
  public void testPieceRotated2IsSame() {
    assertEquals(p1a, p1c);
  }
  
  @Test
  public void testPieceRotated3IsSame() {
    assertEquals(p1b, p1c);
  }

  @Test
  public void testPieceDifferent() {
    assertThat(p1a, not(equalTo(p2)));
  }
}
