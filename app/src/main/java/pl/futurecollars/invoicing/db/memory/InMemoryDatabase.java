package pl.futurecollars.invoicing.db.memory;

import java.util.List;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {
  @Override
  public void save(Invoice invoice) {
  }

  @Override
  public Invoice getById(Integer id) {
    return null;
  }

  @Override
  public List<Invoice> getAll() {
    return null;
  }

  @Override
  public Invoice update(Integer id, Invoice updatedInvoice) {
    return null;
  }

  @Override
  public void delete(Integer id) {

  }
}
