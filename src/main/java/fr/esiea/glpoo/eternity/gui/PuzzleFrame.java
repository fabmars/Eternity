package fr.esiea.glpoo.eternity.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import fr.esiea.glpoo.eternity.domain.Piece;
import fr.esiea.glpoo.eternity.domain.PieceStore;
import fr.esiea.glpoo.eternity.domain.Puzzle;
import fr.esiea.glpoo.eternity.io.CsvParseReport;
import fr.esiea.glpoo.eternity.io.PuzzleDao;
import fr.esiea.glpoo.eternity.io.PuzzleParseContext;
import fr.esiea.glpoo.eternity.io.PuzzleParseReport;

public class PuzzleFrame extends JFrame implements SolutionHandler {

  private static final long serialVersionUID = 1L;

  private RestartActionListener restartActionListener;
  
  //components to be enabled/disabled when game is paused
  private PuzzleTable tableSource;
  private PuzzleTable tableDest;
  private JButton rotateButton;
  private TimerThread timerThread;

  @Deprecated
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        URL stateFile = getClass().getResource("/solution.csv");
        PuzzleFrame pf = new PuzzleFrame();
        pf.openStateFile(stateFile);
        pf.setVisible(true);
      }
    });
  }

  /**
   * Create the frame.
   */
  public PuzzleFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 490, 350);

    // Menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenuItem menuFile = new JMenu("File");
    menuBar.add(menuFile);
    JMenuItem menuOpen = new JMenuItem("Open...");
    menuOpen.addActionListener(new OpenStateFileListener(this));
    menuFile.add(menuOpen);
    // FIXME implement open
    JMenuItem menuSave = new JMenuItem("Save...");
    menuSave.addActionListener(new SaveStateFileListener(this));
    menuFile.add(menuSave);
    // FICME implement save
    JMenuItem menuHelp = new JMenuItem("Help");
    menuHelp.addActionListener(new HelpActionListener());
    menuBar.add(menuHelp);

    // Content pane
    getContentPane().setLayout(new GridBagLayout());
    PuzzleTableModel tmDest = new PuzzleTableModel();
    tableDest = new PuzzleTable(tmDest);
    getContentPane().add(tableDest, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    JPanel rightPane = new JPanel();
    getContentPane().add(rightPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    rightPane.setLayout(new GridBagLayout());

    JLabel timerLabel = new JLabel();
    timerLabel.setFont(timerLabel.getFont().deriveFont(25.f));
    rightPane.add(timerLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    JPanel buttonsPane = new JPanel();
    buttonsPane.setLayout(new GridLayout(2, 2, 5, 5));
    rotateButton = new JButton("Rotate");
    buttonsPane.add(rotateButton);
    JButton restartButton = new JButton("Restart");
    buttonsPane.add(restartButton);
    JButton helpButton = new JButton("Help");
    buttonsPane.add(helpButton);
    rightPane.add(buttonsPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    PuzzleTableModel tmSource = new PuzzleTableModel();
    tableSource = new PuzzleTable(tmSource);
    rightPane.add(tableSource, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.8, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    
    //rotate button
    RotateActionListener rotateActionListener = new RotateActionListener(tableDest, this);
    rotateButton.addActionListener(rotateActionListener);
    tableDest.addMouseListener(rotateActionListener);
    
    //restart button
    restartActionListener = new RestartActionListener(tmSource, tmDest);
    restartButton.addActionListener(restartActionListener);

    //help button
    helpButton.addActionListener(new HelpActionListener());
    
    
    // Drag'n'drop
    tableSource.setDragEnabled(true);
    tableSource.setTransferHandler(new PieceTransferHandler(null));
    tableSource.setDropMode(DropMode.ON);

    tableDest.setDragEnabled(true);
    tableDest.setTransferHandler(new PieceTransferHandler(this));
    tableDest.setDropMode(DropMode.ON);

    timerThread = new TimerThread(timerLabel);
    timerThread.start();

    setGameEnabled(false);
  }

  public void openStateFile(Path stateFile) {
    try {
      openStateFile(stateFile.toUri().toURL());
    }
    catch (MalformedURLException e) {
      DialogUtils.info("Can't handle file: " + stateFile.toString());
    }
  }

  public void openStateFile(URL stateFileUrl) {
    if (stateFileUrl != null) {
      try {
        PuzzleDao dao = new PuzzleDao();
        PuzzleParseReport report = dao.parse(stateFileUrl);

        if (!report.isExceeded()) {
          Puzzle puzzle = report.getOutcome();
          setPuzzle(report.getPieces(), puzzle);
          setGameEnabled(true);
          checkSolved(puzzle);
        }
        else {
          DialogUtils.info("Too many errors loading statefile"); // FIXME detail
        }
      }
      catch (IOException e) {
        DialogUtils.info("Errors reading statefile"); // FIXME detail
      }
    }
    else {
      DialogUtils.info("No file selected");
    }
  }

  public void randomState(URL facesFileUrl, URL piecesFileUrl) {
    if (facesFileUrl != null && piecesFileUrl != null) {
      try {
        PuzzleDao dao = new PuzzleDao();
        PuzzleParseContext context = new PuzzleParseContext(facesFileUrl, piecesFileUrl);
        CsvParseReport<PieceStore> report = dao.parseFacesAndPieces(context, 0);

        if (!report.isExceeded()) {
          PieceStore pieceStore = report.getOutcome();
          pieceStore.shuffle();
          setPuzzle(pieceStore, dao.createOutcome(context));
          setGameEnabled(true);
        }
        else {
          DialogUtils.info("Too many errors loading statefile"); // FIXME detail
        }
      }
      catch (IOException e) {
        DialogUtils.info("Errors reading statefile"); // FIXME detail
      }
    }
    else {
      DialogUtils.info("No file selected");
    }

  }

  protected void setPuzzle(Collection<Piece> allPieces, Puzzle pDest) {
    // first make sure the pieces store contains all the elements of the puzzle
    for (Piece piece : pDest) { // Can't use Collection#containsAll() because there may be null pieces in pDest
      if (piece != null && !allPieces.contains(piece)) {
        throw new IllegalArgumentException("Puzzle and Piece store don't match");
      }
    }

    // then extracting the pieces that aren't yet on the puzzle board
    List<Piece> remainingPieces = new ArrayList<>(pDest.getRows() * pDest.getCols());
    for (Piece piece : allPieces) {
      if (!pDest.contains(piece)) {
        remainingPieces.add(piece);
      }
    }
    while (remainingPieces.size() < pDest.size()) {
      remainingPieces.add(null);
    }

    Puzzle pSource = new Puzzle(pDest.getRows(), pDest.getCols(), remainingPieces);

    restartActionListener.setPuzzles(pSource, pDest);
    restartActionListener.actionPerformed(null);
  }

  public Puzzle getPuzzleSource() {
    return tableSource.getModel().getPuzzle();
  }

  public Puzzle getPuzleDest() {
    return tableDest.getModel().getPuzzle();
  }

  @Override
  public void checkSolved(Puzzle puzzle) {
    if (puzzle.isSolved()) {
      setGameEnabled(false);
      DialogUtils.info("PUZZLE SOLVED, WELL DONE!!!");
    }
  }

  public void setGameEnabled(boolean enabled) {
    rotateButton.setEnabled(enabled);
    tableSource.setDragEnabled(enabled);
    tableDest.setDragEnabled(enabled);
    timerThread.setPaused(!enabled);
  }
}