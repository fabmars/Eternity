package fr.esiea.glpoo.eternity.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ItemStore<I extends Item> implements Collection<I> {

  protected final List<I> items = new LinkedList<>();

  @Override
  public boolean add(I item) {
    return items.add(item);
  }
  
  @Override
  public boolean addAll(Collection<? extends I> c) {
    return items.addAll(c);
  }


  @Override
  public boolean contains(Object item) {
    return items.contains(item);
  }
  
  @Override
  public boolean containsAll(Collection<?> c) {
    return items.containsAll(c);
  }


  public I get(int id) {
    I result = null;
    for(I item : items) {
      if(item.getId() == id) {
        result = item;
        break;
      }
    }
    return result;
  }

  @Override
  public Iterator<I> iterator() {
    return Collections.unmodifiableList(items).iterator();
  }

  public ListIterator<I> listIterator() {
    return Collections.unmodifiableList(items).listIterator();
  }

  @Override
  public int size() {
    return items.size();
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }
  
  public final boolean isUnicity() {
    boolean result = true;
    
    int count = items.size();
    Object[] array = items.toArray();
    
    for(int i = 0; i < count; i++) {
      for(int j = i+1; j < count; j++) {
        if(Objects.equals(array[i], array[j])) {
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
  
  @Override
  @SuppressWarnings("unchecked")
  public I[] toArray() {
    return (I[])items.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return items.toArray(a);
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }
}
