package pl.futurecollars.invoicing.db.mongo;

import com.mongodb.client.MongoCollection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.bson.Document;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.exceptions.InvoiceNotFoundException;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class MongoBasedDatabase implements Database {

  private MongoCollection<Invoice> invoices;
  private MongoIdProvider idProvider;

  @Override
  public Integer save(Invoice invoice) {
    invoice.setId((int) idProvider.getNextIdAndIncrement());
    invoices.insertOne(invoice);
    return null;
  }

  @Override
  public Optional<Invoice> getById(Integer id) {

    return Optional.ofNullable(invoices.find(new Document("_id", id)).first());
  }

  @Override
  public List<Invoice> getAll() {
    return StreamSupport
        .stream(invoices.find().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public void update(Integer id, Invoice updatedInvoice) {
    updatedInvoice.setId(id);
    if (invoices.findOneAndReplace(idFilter(id), updatedInvoice) == null) {
      throw new InvoiceNotFoundException("Id " + id + " does not exist");
    } else {
      invoices.findOneAndReplace(idFilter(id), updatedInvoice);
    }
  }

  @Override
  public void delete(Integer id) {
    if (invoices.findOneAndDelete(idFilter(id)) == null) {
      throw new InvoiceNotFoundException("Id " + id + " does not exist");
    } else {
      invoices.findOneAndDelete(idFilter(id));
    }
  }

  private Document idFilter(long id) {
    return new Document("_id", id);
  }

}
