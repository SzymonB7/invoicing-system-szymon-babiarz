package pl.futurecollars.invoicing.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxBill {

  private BigDecimal income;
  private BigDecimal costs;
  private BigDecimal incomingVat;
  private BigDecimal outgoingVat;
  private BigDecimal earnings;
  private BigDecimal vatToPay;

}
