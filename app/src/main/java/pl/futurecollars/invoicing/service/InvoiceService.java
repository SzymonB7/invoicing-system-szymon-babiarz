package pl.futurecollars.invoicing.service;

import java.util.List;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InvoiceService {

  private final Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public Integer save(Invoice invoice) {
    return database.save(invoice);
  }

  public Invoice getById(Integer id) {
    return database.getById(id);
  }

  public List<Invoice> getAll() {
    return database.getAll();
  }

  public void update(Integer id, Invoice updatedInvoice) {
    database.update(id, updatedInvoice);
  }

  public void delete(Integer id) {
    database.delete(id);
  }

}
