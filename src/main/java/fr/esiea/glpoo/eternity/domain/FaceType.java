package fr.esiea.glpoo.eternity.domain;

public enum FaceType {
  EDGE("B"),
  FACE("F");
  
  private final String code;
  
  private FaceType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static FaceType getByCode(String code) {
    for(FaceType type : values()) {
      if(type.code.equalsIgnoreCase(code)) {
        return type;
      }
    }
    throw new EnumConstantNotPresentException(FaceType.class, code);
  }
  
}
