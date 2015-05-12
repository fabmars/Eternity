package fr.esiea.glpoo.eternity.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.esiea.glpoo.eternity.domain.ItemStore;
import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.Puzzle;
import fr.esiea.glpoo.eternity.io.PuzzleDao;
import fr.esiea.glpoo.eternity.io.PuzzleParseReport;

public class PuzzleFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private PuzzleTableModel tmSource;
  private PuzzleTableModel tmDest;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          Path stateFile = Paths.get("D:/java/workspace/ESIEA/Eternity/src/main/resources/partie.csv");
          PuzzleDao dao = new PuzzleDao();
          PuzzleParseReport report = dao.parse(stateFile);
          
          PuzzleFrame pf = new PuzzleFrame();
          if(!report.isExceeded()) {
            pf.setPuzzle(report.getOutcome(), report.getPieces());
            pf.setVisible(true);
          }
          else {
            JOptionPane.showMessageDialog(null, "Too many errors loading statefile"); //FIXME detail
          }
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
    
    //Menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenuItem menuFile = new JMenu("File");
    menuBar.add(menuFile);
    JMenuItem menuOpen = new JMenuItem("Open...");
    menuFile.add(menuOpen);
    //FIXME implement open
    JMenuItem menuSave = new JMenuItem("Save...");
    menuFile.add(menuSave);
    //FICME implement save
    JMenuItem menuHelp = new JMenuItem("Help");
    menuHelp.addActionListener(new HelpActionListener());
    menuBar.add(menuHelp);
    
    //Content pane
    getContentPane().setLayout(new GridBagLayout());
    tmDest = new PuzzleTableModel();
    PuzzleTable tableDest = new PuzzleTable(tmDest);
    getContentPane().add(tableDest, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    JPanel rightPane = new JPanel();
    getContentPane().add(rightPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    
    rightPane.setLayout(new GridBagLayout());
    
    JLabel timerPane = new JLabel("TIMER");
    rightPane.add(timerPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    
    JPanel buttonsPane = new JPanel();
    buttonsPane.setLayout(new GridLayout(2, 2, 5, 5));
    JButton rotateButton = new JButton("Rotate");
    rotateButton.addActionListener( new RotateActionListener(tableDest));
    buttonsPane.add(rotateButton);
    JButton restartButton = new JButton("Restart");
    buttonsPane.add(restartButton);
    JButton helpButton = new JButton("Help");
    buttonsPane.add(helpButton);
    rightPane.add(buttonsPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    
    tmSource = new PuzzleTableModel();
    PuzzleTable tableSource = new PuzzleTable(tmSource);
    rightPane.add(tableSource, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.8, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    //Drag'n'drop
    new PieceTransferHandler(tableSource, tableDest);
  }
  

  public void setPuzzle(Puzzle pDest, Collection<Piece> allPieces) {
    //first make sure the pieces store contains all the elements of the puzzle
    if(!allPieces.containsAll(pDest)) {
      throw new IllegalArgumentException("Puzzle and Piece store don't match");
    }
    
    //then extracting the pieces that aren't yet on the puzzle board
    List<Piece> remainingPieces = new ArrayList<>(pDest.getRows() * pDest.getCols());
    for(Piece piece : allPieces) {
      if(!pDest.contains(piece)) {
        remainingPieces.add(piece);
      }
    }
    while(remainingPieces.size() < pDest.size()) {
      remainingPieces.add(null);
    }
    
    Puzzle pSource = new Puzzle(pDest.getRows(), pDest.getCols(), remainingPieces);
    setPuzzles(pSource, pDest);
  }

  private void setPuzzles(Puzzle pSource, Puzzle pDest) {
    tmSource.setPuzzle(pSource);
    tmSource.fireTableStructureChanged();

    tmDest.setPuzzle(pDest);
    tmDest.fireTableStructureChanged();
  }
  
  public Puzzle getPuzzleSource() {
    return tmSource.getPuzzle();
  }
  
  public Puzzle getPuzleDest() {
    return tmDest.getPuzzle();
  }
}