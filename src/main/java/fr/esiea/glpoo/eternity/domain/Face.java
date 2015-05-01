package fr.esiea.glpoo.eternity.domain;

import java.awt.Color;
import java.util.Objects;

public class Face extends Item {

  private FaceType type;
  private Color backgroundColor;
  private Pattern pattern; //may be null for type EDGE
  private Color patternColor; //hence may be null too
  
  
  public Face(int id, FaceType type, Color backgroundColor, Pattern pattern, Color patternColor) {
    super( Objects.requireNonNull(id));
    this.type = Objects.requireNonNull(type);
    this.backgroundColor = Objects.requireNonNull(backgroundColor);
    this.pattern = pattern;
    this.patternColor = patternColor;
  }


  public FaceType getType() {
    return type;
  }
  
  public boolean isEdge() {
    return type == FaceType.EDGE;
  }

  public boolean isFace() {
    return type == FaceType.FACE;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public Color getPatternColor() {
    return patternColor;
  }

  /**
   * Choice not to compare the ids, comparing the attributes themselves
   * (that will help checking 2 Pieces are not identical)
   */
  @Override
  public boolean equals(Object obj) {
    Face face = (Face)obj;
    return type.equals(face.type)
        && backgroundColor.equals(face.backgroundColor)
        && pattern.equals(face.pattern)
        && patternColor.equals(face.patternColor);
  }

  @Override
  public String toString() {
    return new StringBuilder()
    .append(getId())
    .append(": ")
    .append(type)
    .append(" ")
    .append(backgroundColor)
    .append(" ")
    .append(pattern)
    .append(" ")
    .append(patternColor)
    .toString();
  }
}
