package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.esiea.glpoo.eternity.domain.Puzzle;

public class RestartActionListener implements ActionListener {

  private PuzzleTableModel sourceTableModel;
  private PuzzleTableModel destTableModel;
  
  private Puzzle originalSourcePuzzle;
  private Puzzle originalDestPuzzle;

  
  public RestartActionListener(PuzzleTableModel sourceTableModel, PuzzleTableModel destTableModel) {
    this.sourceTableModel = sourceTableModel;
    this.destTableModel = destTableModel;
  }

  public void setPuzzles(Puzzle originalSourcePuzzle, Puzzle originalDestPuzzle) {
    this.originalSourcePuzzle = originalSourcePuzzle;
    this.originalDestPuzzle = originalDestPuzzle;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    sourceTableModel.setPuzzle( new Puzzle(originalSourcePuzzle));
    sourceTableModel.fireTableStructureChanged();

    destTableModel.setPuzzle( new Puzzle(originalDestPuzzle));
    destTableModel.fireTableStructureChanged();
  }
}
