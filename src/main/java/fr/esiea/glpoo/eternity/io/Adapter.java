package fr.esiea.glpoo.eternity.io;

public interface Adapter<T> {

  public T getAsObject(String s) throws CsvException;
  public String getAsString(T object) throws CsvException;
}
