package fr.esiea.glpoo.eternity.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @param <T> is the entity type to load
 */
public abstract class GenericDao<T> {

  public static final String DEFAULT_SEPARATOR = ";";
  public static final String DEFAULT_COMMENT = "#";
  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
  public static final int DEFAULT_MAX_ERRORS = 0;

  protected final String separator;
  protected final String comment;
  protected final Charset charset;

  
  public GenericDao() {
    this(DEFAULT_SEPARATOR, DEFAULT_COMMENT, DEFAULT_CHARSET);
  }

  /**
   * @param sepaarator as a regexp
   * @param comment as a string
   */
  public GenericDao(String sepaarator, String comment, Charset charset) {
    this.separator = sepaarator;
    this.comment = comment;
    this.charset = charset;
  }

  public final String getSeparator() {
    return separator;
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
    try(InputStreamReader reader = new InputStreamReader(is, charset)) {
      return parse(reader, maxErrors);
    }
  }

  public final CsvParseReport parse(Reader reader) throws IOException {
    return parse(reader, 0);
  }

  public CsvParseReport parse(Reader reader, int maxErrors) throws IOException {
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

  public final T parseLine(String line) throws CsvException {
    String[] parts = line.split(separator);
    return parseLine(parts);
  }
  
  public abstract T parseLine(String[] parts) throws CsvException;
  public abstract void insert(T entity);
}
