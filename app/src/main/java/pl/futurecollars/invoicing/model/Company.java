package pl.futurecollars.invoicing.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {

  private Integer id;
  private Integer taxIdNumber;
  private String address;

}
