package pl.futurecollars.invoicing.exceptions;

public class InvoiceNotFoundException extends RuntimeException {

  public InvoiceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvoiceNotFoundException(String message) {
    super(message);
  }

}
