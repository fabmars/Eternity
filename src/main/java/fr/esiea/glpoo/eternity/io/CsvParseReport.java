package fr.esiea.glpoo.eternity.io;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CsvParseReport<O> {

  private O outcome;
  private int errorLimit; //allows this number of errors, inclusive
  private List<CsvException> errors = new LinkedList<>(); 

  public CsvParseReport() {
    this(0);
  }
  
  public CsvParseReport(int errorLimit) {
    this.errorLimit = errorLimit;
  }

  public O getOutcome() {
    return outcome;
  }
  
  protected void setOutcome(O outcome) {
    this.outcome = outcome;
  }
  
  public boolean isErrors() {
    return !errors.isEmpty();
  }

  public int getErrorLimit() {
    return errorLimit;
  }
  
  public int getErrorCount() {
    return errors.size();
  }

  /**
   * @return whether there were too many errors, and therefore whether the parsing was interrupted
   */
  public boolean isExceeded() {
    return getErrorCount() > errorLimit;
  }

  /**
   * Adds the exception to the list of exceptions
   * @param e the exception to add
   * @return result of {@link #isExceeded()} after addition
   */
  public boolean addError(CsvException e) {
    errors.add(e);
    return isExceeded();
  }
  
  public boolean addErrors(Collection<CsvException> es) {
    return errors.addAll(es);
  }
  
  public List<CsvException> getErrors() {
    return Collections.unmodifiableList(errors);
  }
}
