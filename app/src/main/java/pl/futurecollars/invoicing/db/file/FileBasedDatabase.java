package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class FileBasedDatabase implements Database {
  private final FileService fileService;
  private final JsonService jsonService;
  private final IdService idService;
  private final Path databasePath;

  public boolean containsId(String line, Integer id) {
    return line.contains("\"id\"" + ":" + id);
  }

  @Override
  public Integer save(Invoice invoice) {
    try {
      invoice.setId(idService.getNextIdAndIncrement());
      String invoiceAsJson = jsonService.writeInvoiceAsJson(invoice);
      fileService.appendLineToFile(databasePath, invoiceAsJson);
      return invoice.getId();
    } catch (IOException e) {
      throw new RuntimeException("Failed to save invoice in database");
    }
  }

  @Override
  public Optional<Invoice> getById(Integer id) {
    try {
      return fileService.readAllLines(databasePath)
          .stream()
          .filter(line -> containsId(line, id))
          .map(jsonService::readJsonAsInvoice)
          .findFirst();
    } catch (IOException e) {
      throw new RuntimeException("Failed to get an invoice from file", e);
    }

  }

  @Override
  public List<Invoice> getAll() {

    try {
      return fileService.readAllLines(databasePath)
          .stream()
          .map(jsonService::readJsonAsInvoice)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Failed to get a list of invoices from file");
    }
  }

  @Override
  public void update(Integer id, Invoice updatedInvoice) {
    try {
      List<String> allInvoicesInDatabase = fileService.readAllLines(databasePath);
      List<String> listOfInvoicesWithoutOneWithGivenID = fileService.readAllLines(databasePath)
          .stream()
          .filter(line -> !containsId(line, id))
          .collect(Collectors.toList());
      if (listOfInvoicesWithoutOneWithGivenID.size() == allInvoicesInDatabase.size()){
        throw new IllegalArgumentException("Id:" + id + "does not exist");
      }
      updatedInvoice.setId(id);
      String invoice = jsonService.writeInvoiceAsJson(updatedInvoice);
      listOfInvoicesWithoutOneWithGivenID.add(invoice);

      fileService.overwriteLinesInFile(databasePath,listOfInvoicesWithoutOneWithGivenID);

    } catch (IOException e) {
      throw new RuntimeException("Failed to update invoice id:" + id + "in database");
    }

  }

  @Override
  public void delete(Integer id) {
    try {
      List<String> allInvoicesInDatabase = fileService.readAllLines(databasePath);
      List<String> listOfInvoicesWithoutOneWithGivenID = fileService.readAllLines(databasePath)
          .stream()
          .filter(line -> !containsId(line, id))
          .collect(Collectors.toList());
      if (listOfInvoicesWithoutOneWithGivenID.size() == allInvoicesInDatabase.size()){
        throw new IllegalArgumentException("Id:" + id + "does not exist");
      }

      fileService.overwriteLinesInFile(databasePath,listOfInvoicesWithoutOneWithGivenID);

    } catch (IOException e) {
      throw new RuntimeException("Failed to delete invoice id:" + id + "from database");
    }


  }
}
