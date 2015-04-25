package fr.esiea.glpoo.eternity.domain;

/**
 * We'll use the ordinal as the index in the Piece.faces array.
 */
public enum Orientation {
  NORTH,
  WEST,
  SOUTH,
  EAST;
  
  
  public Orientation nextClockwise() {
    Orientation[] values = values();
    int size = values.length;
    return values[(ordinal() + 1) % size];
  }
}
