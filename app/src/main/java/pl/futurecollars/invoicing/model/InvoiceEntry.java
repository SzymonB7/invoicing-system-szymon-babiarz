package pl.futurecollars.invoicing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JoinColumn(name = "invoice_entry_id")
  @JsonIgnore
  @ApiModelProperty(value = "Invoice entry id (generated by application)", required = true, example = "1")
  private Long id;

  @ApiModelProperty(value = "Product/service description", required = true, example = "Bun")
  private String description;

  @ApiModelProperty(value = "Number of items", required = true, example = "5")
  private int quantity;

  @ApiModelProperty(value = "Product/service net price", required = true, example = "1200.25")
  private BigDecimal netPrice;

  @ApiModelProperty(value = "Product/service tax value", required = true, example = "96.02")
  @Builder.Default
  private BigDecimal vatValue = BigDecimal.ZERO;

  @Enumerated(EnumType.STRING)
  @ApiModelProperty(value = "Tax rate", required = true)
  private Vat vatRate;

  @JoinColumn(name = "car_expense_is_related_to")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @ApiModelProperty(value = "Car this expense is related to, empty if expense is not related to car")
  private Car carExpenseIsRelatedTo;

}
