package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {

  private Integer id;
  private LocalDate date;
  private Company sellerCompany;
  private Company buyerCompany;
  private List<InvoiceEntry> invoiceEntries;

}
