package pl.futurecollars.invoicing.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TaxBill {

  private BigDecimal income;
  private BigDecimal costs;
  private BigDecimal incomingVat;
  private BigDecimal outgoingVat;
  private BigDecimal earnings;
  private BigDecimal vatToPay;

}
