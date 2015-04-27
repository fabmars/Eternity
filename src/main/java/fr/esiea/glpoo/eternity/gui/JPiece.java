package fr.esiea.glpoo.eternity.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import fr.esiea.glpoo.eternity.domain.Piece;

public class JPiece extends JLabel {

  private static final long serialVersionUID = 1L;
  
  private Piece piece;

  public JPiece(Piece piece) {
    this.piece = piece;
  }

  public Piece getPiece() {
    return piece;
  }

  /**
   * FIXME
   */
  @Override
  public void paint(Graphics g) {
    if(piece != null) {
      Color c = new Color(random(), random(), random());
      g.setColor(c);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
  
  private int random() {
    return (int)(Math.random() * 256);
  }
}
