package fr.esiea.glpoo.eternity.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;

public class TimerThread extends Thread {

  private final static SimpleDateFormat sdf;
  private final Timer timer = new Timer();
  private final JLabel timerLabel;
  
  public TimerThread(JLabel timerLabel) {
    this.timerLabel = timerLabel;
  }
  
  static {
    sdf = new SimpleDateFormat("HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
  }
  
  
  @Override
  public void run() {
    while(true) {
      String sTime = getTimeAsString(timer.getElapsed());
      timerLabel.setText(sTime);
      //updateUI(); //no need
      System.out.println("UPDATE " + timer.getElapsed());
  
      try {
        Thread.sleep(1000L);
      }
      catch (InterruptedException e) {
        //nothing, we're interrupted and there's nothing we can do about it
      }
    }
  }

  private static String getTimeAsString(long timeInMillis) {
    String text = sdf.format(new Date(timeInMillis));

    // works too
    // int elapsed = (int)(timeInMillis / 1000L); //seconds
    // int hours = elapsed / 3600;
    // int minutes = (elapsed/60)%60;
    // int seconds = elapsed % 60;
    // String text = MessageFormat.format("{0,number,00}:{1,number,00}:{2,number,00}", hours, minutes, seconds);

    return text;
  }
  
  @Override
  public synchronized void start() {
    start(0L);
  }

  public synchronized void start(long elapsed) {
    timer.start(elapsed);
    super.start();
  }
  
  public boolean isStarted() {
    return timer.isStarted();
  }

  public boolean isPaused() {
    return timer.isPaused();
  }

  public void setPaused(boolean paused) {
    timer.setPaused(paused);
  }
  
  public void halt() {
    timer.stop();
  }
}
