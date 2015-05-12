package fr.esiea.glpoo.eternity.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @param <I> is the entity type to load
 */
public abstract class GenericDao<I, C, O> {

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
    line = line.trim();
    return line.length() == 0 || line.startsWith(comment);
  }
  

  public final CsvParseReport<O> parse(C context, InputStream is) throws IOException {
    return parse(context, is, DEFAULT_MAX_ERRORS);
  }

  public final CsvParseReport<O> parse(C context, InputStream is, int maxErrors) throws IOException {
    try(InputStreamReader reader = new InputStreamReader(is, charset)) {
      return parse(context, reader, maxErrors);
    }
  }

  public final CsvParseReport<O> parse(C context, Reader reader) throws IOException {
    return parse(context, reader, 0);
  }

  public CsvParseReport<O> parse(C context, Reader reader, int maxErrors) throws IOException {
    CsvParseReport<O> report = new CsvParseReport<>(maxErrors);
    O outcome = createOutcome(context);
    try(BufferedReader br = new BufferedReader(reader)) {
      String line = null;
      while((line = br.readLine()) != null) {
        if(!isComment(line)) {
          try {
            I entity = parseLine(context, line);
            insert(outcome, entity);
          }
          catch(CsvException e) {
            if(report.addError(e)) {
              break;
            }
          }
        }
      }
    }
    report.setOutcome(outcome);
    return report;
  }

  public final I parseLine(C context, String line) throws CsvException {
    String[] parts = line.split(separator);
    return parseLine(context, parts);
  }
  
  public abstract O createOutcome(C context);
  public abstract I parseLine(C context, String[] parts) throws CsvException;
  public abstract void insert(O outcome, I entity);
}
