package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class FileBasedDatabase implements Database {
  private final FileService fileService;
  private final JsonService jsonService;
  private final IdService idService;
  private final Path databasePath;

  @Override
  public Integer save(Invoice invoice) {
    try {
      invoice.setId(idService.getNextIdAndIncreament());
      String invoiceAsJson = jsonService.writeInvoiceAsJson(invoice);
      fileService.appendLineToFile(databasePath, invoiceAsJson);
      return invoice.getId();
    } catch (IOException e) {
      throw new RuntimeException("Failed to save invoice in database");
    }
  }

  @Override
  public Optional<Invoice> getById(Integer id) {
    return null;
  }

  @Override
  public List<Invoice> getAll() {
    return null;
  }

  @Override
  public void update(Integer id, Invoice updatedInvoice) {

  }

  @Override
  public void delete(Integer id) {

  }
}
