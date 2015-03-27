package fr.esiea.glpoo.eternity.domain;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Contains a list of non-null Faces
 */
public class FaceStore {

  private List<Face> faces = new LinkedList<Face>();

  public void add(Face face) {
    if(face != null) {
      faces.add(face);
    }
  }

  public void addAll(Face... faces) {
    for(Face face : faces) {
      add(face);
    }
  }

  public boolean contains(Face f) {
    return faces.contains(f);
  }

  public Face get(int id) {
    Face result = null;
    for(Face face : faces) {
      if(face.getId() == id) {
        result = face;
        break;
      }
    }
    return result;
  }

  public Iterator<Face> iterator() {
    return Collections.unmodifiableList(faces).iterator();
  }

  public ListIterator<Face> listIterator() {
    return Collections.unmodifiableList(faces).listIterator();
  }

  public int size() {
    return faces.size();
  }

  public boolean isEmpty() {
    return faces.isEmpty();
  }
  
  public boolean isUnicity() {
    boolean result = true;
    
    int faceCount = faces.size();
    Face[] faceArray = faces.toArray( new Face[faceCount]);
    
    for(int i = 0; i < faceCount; i++) {
      for(int j = i+1; j < faceCount; j++) {
        if(faceArray[i].equals(faceArray[j])) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
  
  public void shuffle() {
    Collections.shuffle(faces);
  }
}
