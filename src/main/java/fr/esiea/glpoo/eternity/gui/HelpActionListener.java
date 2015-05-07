package fr.esiea.glpoo.eternity.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class HelpActionListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null, "Help yourself!");
  }
}
