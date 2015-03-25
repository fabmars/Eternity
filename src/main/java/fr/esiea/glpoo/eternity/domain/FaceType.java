package fr.esiea.glpoo.eternity.domain;

public enum FaceType {
  EDGE('B'),
  FACE('F');
  
  private char code;
  
  private FaceType(char code) {
    this.code = code;
  }

  public char getCode() {
    return code;
  }
  
  public FaceType getByCode(char code) {
    for(FaceType type : values()) {
      if(type.getCode() == code) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown FaceType: " + code);
  }
}
