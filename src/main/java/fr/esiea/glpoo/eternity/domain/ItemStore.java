package fr.esiea.glpoo.eternity.domain;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ItemStore<T extends Item> implements Iterable<T> {

  private List<T> items = new LinkedList<>();

  public void add(T item) {
    if(item != null) {
      items.add(item);
    }
  }

  public boolean contains(T item) {
    return items.contains(item);
  }

  public T get(int id) {
    T result = null;
    for(T item : items) {
      if(item.getId() == id) {
        result = item;
        break;
      }
    }
    return result;
  }

  @Override
  public Iterator<T> iterator() {
    return Collections.unmodifiableList(items).iterator();
  }

  public ListIterator<T> listIterator() {
    return Collections.unmodifiableList(items).listIterator();
  }

  public int size() {
    return items.size();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }
  
  public final boolean isUnicity() {
    boolean result = true;
    
    int count = items.size();
    Object[] array = items.toArray();
    
    for(int i = 0; i < count; i++) {
      for(int j = i+1; j < count; j++) {
        if(array[i].equals(array[j])) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
  
  public void shuffle() {
    Collections.shuffle(items);
  }
  
  @SuppressWarnings("unchecked")
  public T[] toArray() {
    return (T[])items.toArray();
  }
}
