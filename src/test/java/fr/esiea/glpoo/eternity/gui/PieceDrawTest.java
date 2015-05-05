package fr.esiea.glpoo.eternity.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import fr.esiea.glpoo.eternity.domain.Edge;
import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.Orientation;
import fr.esiea.glpoo.eternity.domain.Pattern;
import fr.esiea.glpoo.eternity.domain.Piece;

public class PieceDrawTest {

  public static void main(String... args) {
    JFrame frame = new JFrame();
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 450, 450);
        
    Face f0 = new Edge(0);
    Face f1 = new Face(1, Color.yellow, Pattern.LINES, Color.blue);
    Face f2 = new Face(2, Color.red, Pattern.TRIANGLE, Color.white);
    Face f3 = new Face(3, Color.blue, Pattern.ZIGZAG, Color.green);
    Piece piece = new Piece(1, f0, f1, f2, f3, Orientation.SOUTH).rotateClockwise().rotateClockwise();

    JPiece jpiece = new JPiece(piece);
    jpiece.setPreferredSize(new Dimension(200, 200));
    frame.setContentPane(jpiece);
    
    frame.pack();
    frame.setVisible(true);
  }
}
