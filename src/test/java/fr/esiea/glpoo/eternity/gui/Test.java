package fr.esiea.glpoo.eternity.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

public class Test extends JFrame {

  private static final long serialVersionUID = 1L;
  
  private JPanel contentPane;
  private MyTable tableDest;
  private MyTable tableSource;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          Test frame = new Test();
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
  public Test() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.5);
    contentPane.add(splitPane, BorderLayout.CENTER);
    
    
    tableSource = new MyTable(true);
    splitPane.setRightComponent(tableSource);

    tableDest = new MyTable(false);
    splitPane.setLeftComponent(tableDest);

    
    TransferHandler transferHandler = new MyTransferHandler();

    tableSource.setDragEnabled(true);
    tableSource.setTransferHandler(transferHandler);
    tableSource.setDropMode(DropMode.ON);

    
    tableDest.setDragEnabled(true);
    tableDest.setTransferHandler(transferHandler);
    tableDest.setDropMode(DropMode.ON);
  }
}
