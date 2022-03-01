package pl.futurecollars.invoicing.db.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import pl.futurecollars.invoicing.model.Invoice;

public class JsonService {
  public String writeInvoiceAsJson(Invoice invoice) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .writeValueAsString(List.of(invoice));
  }

  public Invoice readJsonAsInvoice(String invoiceAsJson) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .readValue(invoiceAsJson, Invoice.class);
  }

}
