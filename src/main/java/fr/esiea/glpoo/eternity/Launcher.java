package fr.esiea.glpoo.eternity;

import java.awt.EventQueue;
import java.net.URL;

import fr.esiea.glpoo.eternity.gui.PuzzleFrame;

public class Launcher {

  //private static final String ZERO_CSV = "zero.csv";
  private static final String FACES_CSV = "faces.csv";
  private static final String PIECES_CSV = "pieces.csv";


  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        PuzzleFrame pf = new PuzzleFrame();
        URL facesFile = getResource(FACES_CSV);
        URL piecesFile = getResource(PIECES_CSV);
        pf.randomState(facesFile, piecesFile);

        //URL zeroFile = getResource(ZERO_CSV);
        //pf.openStateFile(zeroFile);
        
        pf.setVisible(true);
      }
    
      private URL getResource(String fileName) {
        return Launcher.class.getResource("/" + fileName);
      }
    });
  }
}
