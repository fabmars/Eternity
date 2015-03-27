package fr.esiea.glpoo.eternity.domain;

import java.util.Arrays;
import java.util.Objects;

public class Piece {

  public final static int NORTH = 0;
  public final static int WEST = NORTH + 1;
  public final static int SOUTH = WEST + 1;
  public final static int EAST = SOUTH + 1;
  
  private int id;
  private Face[] faces; //order: NWSE
  //FIXME ADD ORIENTATION
  
  
  /**
   * copy ctor
   * @param p
   */
  public Piece(Piece p) {
    this(p.id, p.getNorth(), p.getWest(), p.getSouth(), p.getEast());
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
    faces = new Face[]{Objects.requireNonNull(northFace),
                       Objects.requireNonNull(westFace),
                       Objects.requireNonNull(southFace),
                       Objects.requireNonNull(eastFace)};
  }

  public int getId() {
    return id;
  }
  
  public Face getNorth() {
    return faces[NORTH];
  }
  
  public Face getWest() {
    return faces[WEST];
  }
  
  public Face getSouth() {
    return faces[SOUTH];
  }
  
  public Face getEast() {
    return faces[EAST];
  }
  
  /**
   * @return Caution, returns itself after rotation!
   */
  public Piece rotateClockwise() {
    Face northFace = faces[NORTH];
    faces[NORTH] = faces[WEST];
    faces[WEST] = faces[SOUTH];
    faces[SOUTH] = faces[EAST];
    faces[EAST] = northFace;
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
    .append(id)
    .append(": ")
    .append(faces)
    .toString();
  }
}
