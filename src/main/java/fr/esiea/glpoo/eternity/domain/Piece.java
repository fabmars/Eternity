package fr.esiea.glpoo.eternity.domain;

import static fr.esiea.glpoo.eternity.domain.Orientation.EAST;
import static fr.esiea.glpoo.eternity.domain.Orientation.NORTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.SOUTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.WEST;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;

public class Piece extends Item implements Iterable<Face> {
  
  private final Hashtable<Orientation, Face> faces = new Hashtable<>();
  private Orientation orientation; //only this property is altered in case of rotation
  
  
  /**
   * copy ctor
   * @param p
   */
  public Piece(Piece p) {
    this(p, p.orientation);
  }

  public Piece(Piece p, Orientation orientation) {
    super(p.getId());
    for(Orientation or : Orientation.values()) {
      faces.put(or, p.getFaceAbsolute(or));
    }
    this.orientation = orientation;
  }

  /**
   * ctor
   * @param id
   * @param northFace
   * @param westFace
   * @param southFace
   * @param eastFace
   * @param orientation
   */
  public Piece(int id, Face northFace, Face eastFace, Face southFace, Face westFace, Orientation orientation) {
    super(id);
    faces.put(Orientation.NORTH, Objects.requireNonNull(northFace));
    faces.put(Orientation.EAST,  Objects.requireNonNull(eastFace));
    faces.put(Orientation.SOUTH, Objects.requireNonNull(southFace));
    faces.put(Orientation.WEST,  Objects.requireNonNull(westFace));
    this.orientation = Objects.requireNonNull(orientation);
  }


  /**
   * ctor
   * @param id
   * @param northFace
   * @param westFace
   * @param southFace
   * @param eastFace
   */
  public Piece(int id, Face northFace, Face eastFace, Face southFace, Face westFace) {
    this(id, northFace, eastFace, southFace, westFace, Orientation.NORTH);
  }

  
  @Override
  public Iterator<Face> iterator() {
    return new FaceIterator(this);
  }
  
  /**
   * This gives the face, taking the piece's orientation into account
   */
  public Face getFace(Orientation orientation) {
    return getFaceAbsolute(orientation.sub(this.orientation)); //in this order ! 
  }

  private Face getFaceAbsolute(Orientation orientation) {
    return faces.get(orientation);
  }
  
  public Face getNorth() {
    return getFace(NORTH);
  }
  
  public Face getEast() {
    return getFace(EAST);
  }

  public Face getSouth() {
    return getFace(SOUTH);
  }

  public Face getWest() {
    return getFace(WEST);
  }


  public Orientation getOrientation() {
    return orientation;
  }

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  /**
   * @return itself after rotation, caution!
   */
  public Piece rotateClockwise() {
    setOrientation(orientation.rotateClockwise());
    return this;
  }
  
  /**
   * Taking into account the fact the two pieces may have different orientations
   */
  @Override
  public boolean equals(Object other) {
    if(other != null) {
      Piece copy = new Piece(((Piece)other)); //this is a copy, we don't want to alter the original
      
      for(Orientation or : Orientation.values()) {
        copy.setOrientation(or);
  
        if(equalsOriented(copy)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean equalsOriented(Piece piece) {
    for(Orientation or : Orientation.values()) {
      if(!getFace(or).equals(piece.getFace(or))) {
        return false;
      }
    }
    return true;
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
