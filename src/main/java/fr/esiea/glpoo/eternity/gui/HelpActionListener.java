package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpActionListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    DialogUtils.info("Help yourself!");
  }
}
