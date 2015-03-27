package fr.esiea.glpoo.eternity.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @param <T> is the entity type to load
 */
public abstract class GenericDao<T> {

  public static final String DEFAULT_SEAPRATOR = ";";
  public static final String DEFAULT_COMMENT = "#";
  public static final int DEFAULT_MAX_ERRORS = 0;

  protected final String seaprator;
  protected final String comment;

  public GenericDao() {
    this(DEFAULT_SEAPRATOR, DEFAULT_COMMENT);
  }

  /**
   * @param seaprator as a regexp
   * @param comment as a string
   */
  public GenericDao(String seaprator, String comment) {
    this.seaprator = seaprator;
    this.comment = comment;
  }

  public final String getSeaprator() {
    return seaprator;
  }

  public final String getComment() {
    return comment;
  }
  
  public final boolean isComment(String line) {
    return line.trim().startsWith(comment);
  }
  

  public final CsvParseReport parse(InputStream is) throws IOException {
    return parse(is, DEFAULT_MAX_ERRORS);
  }

  public final CsvParseReport parse(InputStream is, int maxErrors) throws IOException {
    try(InputStreamReader reader = new InputStreamReader(is)) {
      return parse(reader, maxErrors);
    }
  }

  public final CsvParseReport parse(Reader reader) throws IOException {
    return parse(reader, 0);
  }

  public final CsvParseReport parse(Reader reader, int maxErrors) throws IOException {
    CsvParseReport result = new CsvParseReport(maxErrors);
    try(BufferedReader br = new BufferedReader(reader)) {
      String line = null;
      while((line = br.readLine()) != null) {
        if(!isComment(line)) {
          try {
            T entity = parseLine(line);
            insert(entity);
          }
          catch(CsvException e) {
            if(result.addError(e)) {
              break;
            }
          }
        }
      }
    }
    return result;
  }

  public T parseLine(String line) throws CsvException {
    String[] parts = line.split(seaprator);
    return parseLine(parts);
  }
  
  public abstract T parseLine(String[] parts) throws CsvException;
  public abstract void insert(T entity);
}
