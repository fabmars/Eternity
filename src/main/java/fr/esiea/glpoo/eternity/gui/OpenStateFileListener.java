package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class OpenStateFileListener implements ActionListener {

  private PuzzleFrame pf;
  
  public OpenStateFileListener(PuzzleFrame pf) {
    this.pf = pf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Path stateFile = DialogUtils.chooseFile(null, "Open state file...");
    if(stateFile != null) {
      pf.openStateFile(stateFile);
    }
  }
}
