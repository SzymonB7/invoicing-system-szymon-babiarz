package pl.futurecollars.invoicing.db.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import pl.futurecollars.invoicing.model.Invoice;

public class JsonService {
  private final ObjectMapper objectMapper;

  {
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public String writeInvoiceAsJson(Invoice invoice) {

    try {
      return objectMapper.writeValueAsString(List.of(invoice));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to write invoice to JSON", e);
    }
  }

  public Invoice readJsonAsInvoice(String invoiceAsJson) {
    try {
      return objectMapper.readValue(invoiceAsJson, Invoice.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to parse JSON", e);
    }
  }

}
