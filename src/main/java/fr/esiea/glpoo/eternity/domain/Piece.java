package fr.esiea.glpoo.eternity.domain;

import java.util.Objects;

public class Piece {

  public final static int NORTH = 0;
  public final static int WEST = 1;
  public final static int SOUTH = 2;
  public final static int EAST = 3;
  
  private int id;
  private Face[] faces; //order: NWSE
  
  /**
   * copy ctor
   * @param p
   */
  public Piece(Piece p) {
    this(p.id, p.getNorth(), p.getWest(), p.getSouth(), p.getEast());
  }
  
  public Piece(int id, Face northFace, Face westFace, Face southFace, Face eastFace) {
    Objects.requireNonNull(northFace);
    Objects.requireNonNull(westFace);
    Objects.requireNonNull(southFace);
    Objects.requireNonNull(eastFace);
    
    faces = new Face[]{northFace, westFace, southFace, eastFace};
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
  
  public void rotate() {
    Face northFace = faces[NORTH];
    faces[NORTH] = faces[WEST];
    faces[WEST] = faces[SOUTH];
    faces[SOUTH] = faces[EAST];
    faces[EAST] = northFace;
  }
  
  /**
   * Choice not to compare the ids but directly the faces,
   * taking into account the fact they may be rotated with one-another
   */
  @Override
  public boolean equals(Object obj) {
    Piece p = new Piece((Piece)obj); //copy not to alter the original
    
    for(int i = 0; i < 4; i++) {
      if(getNorth().equals(p.getNorth())
      && getWest().equals(p.getWest())
      && getSouth().equals(p.getSouth())
      && getEast().equals(p.getEast())) {
        return true;
      }
      else {
        p.rotate();
      }
    }
    
    return false;
  }

  @Override
  public int hashCode() {
    return id;
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
