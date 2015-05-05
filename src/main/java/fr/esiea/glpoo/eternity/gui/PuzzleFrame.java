package fr.esiea.glpoo.eternity.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import fr.esiea.glpoo.eternity.domain.Face;
import fr.esiea.glpoo.eternity.domain.FaceType;
import fr.esiea.glpoo.eternity.domain.Orientation;
import fr.esiea.glpoo.eternity.domain.Pattern;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private JPanel contentPane;
  private PuzzleTable tableDest;
  private PuzzleTable tableSource;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          PuzzleFrame frame = new PuzzleFrame();
          frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public PuzzleFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.5);
    contentPane.add(splitPane, BorderLayout.CENTER);
    

    Puzzle pSource = new Puzzle(4, 4);
    tableSource = new PuzzleTable(pSource);
    splitPane.setRightComponent(tableSource);

    Puzzle pDest = new Puzzle(4, 4);
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 4; j++) {
        Face f0 = new Face(0, FaceType.values()[(int)(Math.random() * 2)], Color.white, Pattern.CROWN, Color.black);
        Face f1 = new Face(0, FaceType.values()[(int)(Math.random() * 2)], Color.white, Pattern.LINES, Color.black);
        Face f2 = new Face(0, FaceType.values()[(int)(Math.random() * 2)], Color.white, Pattern.TRIANGLE, Color.black);
        Face f3 = new Face(0, FaceType.values()[(int)(Math.random() * 2)], Color.white, Pattern.ZIGZAG, Color.black);
        Piece piece = new Piece(i*4+j, f0, f1, f2, f3, Orientation.NORTH);
        pDest.setPiece(piece, i, j);
      }
    }
    tableDest = new PuzzleTable(pDest);
    splitPane.setLeftComponent(tableDest);

    
    PieceTransferHandler transferHandler = new PieceTransferHandler();

    tableSource.setDragEnabled(true);
    tableSource.setTransferHandler(transferHandler);
    tableSource.setDropMode(DropMode.ON);

    
    tableDest.setDragEnabled(true);
    tableDest.setTransferHandler(transferHandler);
    tableDest.setDropMode(DropMode.ON);
    
  }

}
