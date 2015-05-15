package fr.esiea.glpoo.eternity.domain;

/**
 * We'll use the ordinal as the index in the Piece.faces array.
 * Note they are defined in a clockwise manner
 */
public enum Orientation {
  NORTH,
  EAST,
  SOUTH,
  WEST;
  
  
  public Orientation rotateClockwise() {
    return rotateClockwise(1);
  }
  
  /**
   * Performs some sort of "product" of 2 orientations, so the SOUTH of SOUTH is NORTH. 
   * @param orientation
   * @return
   */
  public Orientation add(Orientation orientation) {
    return rotateClockwise(orientation.ordinal());
  }

  public Orientation sub(Orientation orientation) {
    return rotateClockwise(values().length-orientation.ordinal());
  }

  public Orientation rotateClockwise(int times) {
    Orientation[] values = Orientation.values();
    int size = values.length;
    return values[(ordinal() + times) % size];
  }
  
  public static Orientation random() {
    int randIdx = (int)(Math.random() * 4.0);
    return Orientation.values()[randIdx];
  }
}
