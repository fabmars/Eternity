package fr.esiea.glpoo.eternity.domain;

public class EternityException extends Exception {

  private static final long serialVersionUID = 1L;

  public EternityException(String message) {
    super(message);
  }

  public EternityException(Throwable cause) {
    super(cause);
  }

  public EternityException(String message, Throwable cause) {
    super(message, cause);
  }
}
