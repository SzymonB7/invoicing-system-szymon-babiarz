package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "Invoice id (generated by application)", required = true, example = "1")
  private Long id;

  @ApiModelProperty(value = "Invoice number assigned by user", required = true, example = "2022/05/02/0000001")
  private String number;

  @ApiModelProperty(value = "Date when invoice was created", required = true)
  private LocalDate date;

  @JoinColumn(name = "buyer")
  @OneToOne(cascade = CascadeType.ALL)
  @ApiModelProperty(value = "Company which bought the product/service", required = true)
  private Company buyer;

  @JoinColumn(name = "seller")
  @OneToOne(cascade = CascadeType.ALL)
  @ApiModelProperty(value = "Company which sold the product/service", required = true)
  private Company seller;

  @JoinTable(name = "invoice_invoice_entry", inverseJoinColumns = @JoinColumn(name = "invoice_entry_id"))
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @ApiModelProperty(value = "List of products/services", required = true)
  private List<InvoiceEntry> invoiceEntries;

}
