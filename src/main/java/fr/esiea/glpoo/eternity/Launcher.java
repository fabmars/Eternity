package fr.esiea.glpoo.eternity;

import java.awt.EventQueue;

import fr.esiea.glpoo.eternity.gui.PuzzleFrame;

public class Launcher {

  public Launcher() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        PuzzleFrame pf = new PuzzleFrame();
        pf.setVisible(true);
      }
    });
  }
}
