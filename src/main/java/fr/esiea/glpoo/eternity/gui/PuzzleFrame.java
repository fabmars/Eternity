package fr.esiea.glpoo.eternity.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import fr.esiea.glpoo.eternity.domain.Puzzle;

public class PuzzleFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private JPanel contentPane;
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
    

    tmSource = new PuzzleTableModel();
    PuzzleTable tableSource = new PuzzleTable(tmSource);
    splitPane.setRightComponent(tableSource);
    tmDest = new PuzzleTableModel();
    PuzzleTable tableDest = new PuzzleTable(tmDest);
    splitPane.setLeftComponent(tableDest);
    
    PieceTransferHandler transferHandler = new PieceTransferHandler();

    tableSource.setDragEnabled(true);
    tableSource.setTransferHandler(transferHandler);
    tableSource.setDropMode(DropMode.ON);

    
    tableDest.setDragEnabled(true);
    tableDest.setTransferHandler(transferHandler);
    tableDest.setDropMode(DropMode.ON);
  }

  public void setPuzzles(Puzzle pSource, Puzzle pDest) {
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