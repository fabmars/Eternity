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
  
  public Orientation rotateClockwise(Orientation orientation) {
    return rotateClockwise(orientation.ordinal());
  }

  public Orientation rotateClockwise(int times) {
    Orientation[] values = values();
    int size = values.length;
    return values[(ordinal() + times) % size];
  }
}
