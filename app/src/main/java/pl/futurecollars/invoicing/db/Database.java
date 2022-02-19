package pl.futurecollars.invoicing.db;

import java.util.List;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {
  void save (Invoice invoice);
  Invoice getById (Integer id);
  List<Invoice> getAll();
  Invoice update(Integer id, Invoice updatedInvoice);
  void delete (Integer id);

}
