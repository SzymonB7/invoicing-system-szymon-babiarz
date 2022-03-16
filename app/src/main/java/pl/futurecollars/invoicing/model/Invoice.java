package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Invoice {

  private Integer id;
  private LocalDate date;
  private Company sellerCompany;
  private Company buyerCompany;
  private List<InvoiceEntry> invoiceEntries;

  public Invoice(LocalDate date, Company sellerCompany, Company buyerCompany,
                 List<InvoiceEntry> invoiceEntries) {
    this.date = date;
    this.sellerCompany = sellerCompany;
    this.buyerCompany = buyerCompany;
    this.invoiceEntries = invoiceEntries;
  }
}
