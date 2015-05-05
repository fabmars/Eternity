package fr.esiea.glpoo.eternity.domain;

import static fr.esiea.glpoo.eternity.domain.Orientation.EAST;
import static fr.esiea.glpoo.eternity.domain.Orientation.NORTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.SOUTH;
import static fr.esiea.glpoo.eternity.domain.Orientation.WEST;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Piece extends Item implements Iterable<Face> {
  
  private Face[] faces; //order: this of the Orientation enum elements
  private Orientation orientation; //only this property is altered in case of rotation
  
  
  /**
   * copy ctor
   * @param p
   */
  public Piece(Piece p) {
    super(p.getId());
    this.faces = Arrays.copyOf(p.faces, p.faces.length);
    this.orientation = p.orientation;
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
    faces = new Face[]{Objects.requireNonNull(northFace),
                       Objects.requireNonNull(eastFace),
                       Objects.requireNonNull(southFace),
                       Objects.requireNonNull(westFace)};
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
    return faces[orientation.ordinal()];
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
    Piece copy = new Piece(((Piece)other)); //this is a copy, we don't want to alter the original
    
    for(Orientation or : Orientation.values()) {
      copy.setOrientation(or);

      if(equalsOriented(copy)) {
        return true;
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
