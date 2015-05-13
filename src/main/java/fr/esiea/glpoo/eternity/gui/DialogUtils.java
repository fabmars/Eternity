package fr.esiea.glpoo.eternity.gui;

import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogUtils {

  public static Path chooseFile(Path currentDir, String frameTitle) {
    Path chosenPath = null;
    
    JFileChooser fc = new JFileChooser(currentDir != null ? currentDir.toFile() : null);
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fc.setMultiSelectionEnabled(false);
    JFrame chooserFrame = new JFrame(frameTitle);//to have it on the task bar
    chooserFrame.setUndecorated(true);
    chooserFrame.setVisible(true);

    int result = fc.showOpenDialog(chooserFrame);
    chooserFrame.dispose();

    if (result == JFileChooser.APPROVE_OPTION) {
      chosenPath = fc.getSelectedFile().toPath();
    }
    return chosenPath;
  }
  
  public static void info(String message) {
    JOptionPane.showMessageDialog(null, message);
  }
}
