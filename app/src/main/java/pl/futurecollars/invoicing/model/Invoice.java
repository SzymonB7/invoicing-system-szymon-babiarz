package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

  @ApiModelProperty(value = "Invoice id (generated by application)", required = true, example = "1")
  private Integer id;
  @ApiModelProperty(value = "Date when invoice was created", required = true)
  private LocalDate date;
  @ApiModelProperty(value = "Company which bought the product/service", required = true)
  private Company buyerCompany;
  @ApiModelProperty(value = "Company which sold the product/service", required = true)
  private Company sellerCompany;
  @ApiModelProperty(value = "List of products/services", required = true)
  private List<InvoiceEntry> invoiceEntries;

  public Invoice(LocalDate date, Company sellerCompany, Company buyerCompany,
                 List<InvoiceEntry> invoiceEntries) {
    this.date = date;
    this.sellerCompany = sellerCompany;
    this.buyerCompany = buyerCompany;
    this.invoiceEntries = invoiceEntries;
  }

  public static InvoiceBuilder builder() {
    return new InvoiceBuilder();
  }

  public static class InvoiceBuilder {
    private LocalDate date;
    private Company sellerCompany;
    private Company buyerCompany;
    private List<InvoiceEntry> invoiceEntries;

    InvoiceBuilder() {
    }

    public InvoiceBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public InvoiceBuilder sellerCompany(Company sellerCompany) {
      this.sellerCompany = sellerCompany;
      return this;
    }

    public InvoiceBuilder buyerCompany(Company buyerCompany) {
      this.buyerCompany = buyerCompany;
      return this;
    }

    public InvoiceBuilder invoiceEntries(List<InvoiceEntry> invoiceEntries) {
      this.invoiceEntries = invoiceEntries;
      return this;
    }

    public Invoice build() {
      return new Invoice(date, sellerCompany, buyerCompany, invoiceEntries);
    }

    public String toString() {
      return "Invoice.InvoiceBuilder(id=" + ", date=" + this.date + ", sellerCompany=" + this.sellerCompany + ", buyerCompany="
          + this.buyerCompany + ", invoiceEntries=" + this.invoiceEntries + ")";

    }
  }
}
