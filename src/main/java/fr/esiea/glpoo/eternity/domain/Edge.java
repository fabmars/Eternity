package fr.esiea.glpoo.eternity.domain;

import java.awt.Color;

public class Edge extends Face {
  
  public Edge(int id) {
    super(id, FaceType.EDGE, Color.black, null, null);
  }
}