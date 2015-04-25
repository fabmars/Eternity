package fr.esiea.glpoo.eternity.domain;

import static fr.esiea.glpoo.eternity.domain.Orientation.EAST;
import static fr.esiea.glpoo.eternity.domain.Orientation.NORTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.SOUTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.WEST;

import java.util.Arrays;
import java.util.Objects;

public class Piece extends Item {
  
  private Face[] faces; //order: this of the Orientation enum elements
  private Orientation orientation;
  
  
  /**
   * copy ctor
   * @param p
   */
  public Piece(Piece p) {
    this(p.getId(), p.getNorth(), p.getWest(), p.getSouth(), p.getEast());
  }

  /**
   * ctor
   * @param id
   * @param northFace
   * @param westFace
   * @param southFace
   * @param eastFace
   */
  public Piece(int id, Face northFace, Face westFace, Face southFace, Face eastFace) {
    super(id);
    faces = new Face[]{Objects.requireNonNull(northFace),
                       Objects.requireNonNull(westFace),
                       Objects.requireNonNull(southFace),
                       Objects.requireNonNull(eastFace)};
  }

  public Face getNorth() {
    return faces[Orientation.NORTH.ordinal()];
  }
  
  public Face getWest() {
    return faces[Orientation.WEST.ordinal()];
  }
  
  public Face getSouth() {
    return faces[Orientation.SOUTH.ordinal()];
  }
  
  public Face getEast() {
    return faces[Orientation.EAST.ordinal()];
  }
  
  /**
   * @return Caution, returns itself after rotation!
   */
  public synchronized Piece rotateClockwise() {
    Face northFace = faces[Orientation.NORTH.ordinal()];
    faces[NORTH.ordinal()] = faces[Orientation.WEST.ordinal()];
    faces[WEST.ordinal()] = faces[Orientation.SOUTH.ordinal()];
    faces[SOUTH.ordinal()] = faces[Orientation.EAST.ordinal()];
    faces[EAST.ordinal()] = northFace;
    
    orientation = orientation.nextClockwise();
    
    return this;
  }
  
  /**
   * Choice not to compare the ids but directly the faces,
   * taking into account the fact they may be rotated with one-another
   */
  @Override
  public boolean equals(Object other) {
    Piece otherCopy = new Piece((Piece)other); //copy, not to alter the original
    
    for(int i = 0; i < 4; i++) {
      if(Arrays.equals(faces, otherCopy.faces)) {
        return true;
      }
      else {
        otherCopy.rotateClockwise();
      }
    }
    
    return false;
  }

  @Override
  public String toString() {
    return new StringBuilder()
    .append(getId())
    .append(": ")
    .append(faces)
    .toString();
  }
}
