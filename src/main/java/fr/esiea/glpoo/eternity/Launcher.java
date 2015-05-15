package fr.esiea.glpoo.eternity;

import java.awt.EventQueue;
import java.net.URL;

import fr.esiea.glpoo.eternity.gui.PuzzleFrame;

public class Launcher {

  private static final String ZERO_CSV = "zero.csv";


  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        PuzzleFrame pf = new PuzzleFrame();
        URL zeroFile = getClass().getResource("/" + ZERO_CSV);
        pf.openStateFile(zeroFile);
        pf.setVisible(true);
      }
    });
  }
}
