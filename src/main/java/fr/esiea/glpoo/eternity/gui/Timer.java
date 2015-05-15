package fr.esiea.glpoo.eternity.gui;


public class Timer {
  private boolean started; // tells if the times is started or not
  private long pauseTime; // time at which the pause started
  private long baseTime; // this is the start time shifted by the total pause length
  private long startTime; // this is the REAL start time

  
  public synchronized void start() {
    start(0L);
  }

  public synchronized void start(long elapsed) {
    if (!isStarted()) {
      startTime = baseTime = (getTime() - elapsed);
      started = true;
    }
    else if (isPaused()) {
      setPaused(false);
    }
  }

  public synchronized boolean isStarted() {
    return started;
  }

  public synchronized void pause() {
    setPaused(true);
  }

  public synchronized void setPaused(boolean pause) {
    if (started) {
      if (pause) {
        if (!isPaused()) {
          pauseTime = getTime();
        }
      }
      else if (isPaused()) {
        baseTime = getTime() - (pauseTime - baseTime);
        pauseTime = 0L;
      }
    }
  }

  public synchronized boolean isPaused() {
    return (pauseTime > 0L);
  }

  public synchronized void stop() {
    started = false;
  }

  public synchronized long getStartTime() {
    if (started) {
      return startTime;
    }
    else {
      return 0L;
    }
  }

  public synchronized long getElapsed() {
    long result;

    if (started) {
      if (isPaused()) {
        result = pauseTime - baseTime;
      }
      else {
        result = getTime() - baseTime;
      }
    }
    else {
      result = 0L;
    }

    return result;
  }

  /**
   * Taking System's source, that will be good enough
   * @return
   */
  private long getTime() {
    return System.currentTimeMillis();
  }
}
