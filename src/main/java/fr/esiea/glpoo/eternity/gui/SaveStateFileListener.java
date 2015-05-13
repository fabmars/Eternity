package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import fr.esiea.glpoo.eternity.domain.Puzzle;
import fr.esiea.glpoo.eternity.io.CsvException;
import fr.esiea.glpoo.eternity.io.PuzzleDao;

public class SaveStateFileListener implements ActionListener {

  private PuzzleFrame pf;
  
  public SaveStateFileListener(PuzzleFrame pf) {
    this.pf = pf;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    Puzzle puzzle = pf.getPuzleDest();
    Path stateFile = DialogUtils.chooseFile(puzzle.getStateFile().getParent(), "Choose state file");
    if(stateFile != null) {
      if(!Files.exists(stateFile) || overwriteQuestion()) {
        try {
          new PuzzleDao().save(puzzle, stateFile);
        }
        catch (IOException | CsvException ex) {
          DialogUtils.info("Error saving state file: " + ex.getMessage());
        }
      }
      //else do nothing
    }
    //else do nothing
  }
  
  
  private static boolean overwriteQuestion() {
    int result = JOptionPane.showOptionDialog(null, "File exists. Overwrite?", "Overwrite?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
    return (result == JOptionPane.YES_OPTION); 
  }
}
