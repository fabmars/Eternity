package fr.esiea.glpoo.eternity.domain;

public enum Pattern {
  TRIANGLE("triangle"),
  ZIGZAG("zigzag"),
  CROWN("couronne"),
  LINES("lignes");
  
  private final String code;
  
  private Pattern(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
  
  public static Pattern getByCode(String code) {
    for(Pattern pattern : values()) {
      if(pattern.code.equalsIgnoreCase(code)) {
        return pattern;
      }
    }
    throw new EnumConstantNotPresentException(Pattern.class, code);
  }
  
}
