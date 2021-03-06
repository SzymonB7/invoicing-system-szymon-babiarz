package pl.futurecollars.invoicing.helpers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import pl.futurecollars.invoicing.model.Car;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;
import pl.futurecollars.invoicing.model.Vat;

public class TestHelpers {

  public static Company company1 =
      new Company(null, "Pampers", "11", "Koncertowa 6/8, 80-301 Warszawa", BigDecimal.ZERO.setScale(2), BigDecimal.ZERO.setScale(2));
  public static Company company2 =
      new Company(null, "Mars", "22", "Slowackiego 6/8, 80-302 Katowice", BigDecimal.ZERO.setScale(2), BigDecimal.ZERO.setScale(2));
  public static Company company3 =
      new Company(null, "Apple", "33", "Czarnogorska 6/8, 80-303 Gdansk", BigDecimal.ZERO.setScale(2), BigDecimal.ZERO.setScale(2));
  public static Company company4 =
      new Company(null, "NewCompany1", "44", "Nowa 6/8, 80-303 Gdansk", BigDecimal.valueOf(13000).setScale(2), BigDecimal.ZERO.setScale(2));
  public static Company company5 =
      new Company(null, "NewCompany2", "55", "Stara 6/8, 80-303 Gdansk", BigDecimal.valueOf(30000).setScale(2), BigDecimal.ZERO.setScale(2));

  public static Car car1 = new Car(null, "DW123CT", true);
  public static Car car2 = new Car(null, "DW122FC", true);
  public static Car car3 = new Car(null, "DW345JK", true);

  public static InvoiceEntry invoiceEntry1 =
      new InvoiceEntry(null, "Baton", 1, BigDecimal.valueOf(100).setScale(2), BigDecimal.valueOf(100 * 0.08).setScale(2), Vat.VAT_8, null);
  public static InvoiceEntry invoiceEntry2 =
      new InvoiceEntry(null, "Chleb", 1, BigDecimal.valueOf(200).setScale(2), BigDecimal.valueOf(200 * 0.05).setScale(2), Vat.VAT_5, null);
  public static InvoiceEntry invoiceEntry3 =
      new InvoiceEntry(null, "Kakao", 1, BigDecimal.valueOf(300).setScale(2), BigDecimal.valueOf(300 * 0.23).setScale(2), Vat.VAT_23, null);
  public static InvoiceEntry invoiceEntry4 =
      new InvoiceEntry(null, "fuel", 1, BigDecimal.valueOf(123).setScale(2), BigDecimal.valueOf(123 * 0.08).setScale(2), Vat.VAT_8, car1);
  public static InvoiceEntry invoiceEntry5 =
      new InvoiceEntry(null, "fuel", 1, BigDecimal.valueOf(4000).setScale(2), BigDecimal.valueOf(4000 * 0.05).setScale(2), Vat.VAT_5, car2);
  public static InvoiceEntry invoiceEntry6 =
      new InvoiceEntry(null, "fuel", 1, BigDecimal.valueOf(5500).setScale(2), BigDecimal.valueOf(5500 * 0.23).setScale(2), Vat.VAT_23, car3);

  public static Invoice invoice1 =
      new Invoice(1L, "2020/03/08/0000001", LocalDate.of(2020, 10, 11), company2, company1, List.of(invoiceEntry1, invoiceEntry2, invoiceEntry3));
  public static Invoice invoice2 =
      new Invoice(2L, "2020/03/08/0000002", LocalDate.of(2019, 11, 15), company2, company3, List.of(invoiceEntry1, invoiceEntry2, invoiceEntry3));
  public static Invoice invoice3 =
      new Invoice(3L, "2020/03/08/0000003", LocalDate.of(2020, 12, 13), company3, company2, List.of(invoiceEntry1, invoiceEntry2));
  public static Invoice invoice4 = new Invoice(4L, "2020/03/08/0000004", LocalDate.of(2020, 12, 13), company3, company1, List.of(invoiceEntry3));
  public static Invoice invoice5 = new Invoice(5L, "2020/03/08/0000005", LocalDate.of(2020, 12, 13), company1, company2, List.of(invoiceEntry3));

  public static Invoice invoice6 =
      new Invoice(6L, "2020/03/08/0000006", LocalDate.of(2022, 5, 10), company3, company2, List.of(invoiceEntry4, invoiceEntry5));
  public static Invoice invoice7 = new Invoice(7L, "2020/03/08/0000007", LocalDate.of(2022, 5, 12), company2, company4, List.of(invoiceEntry5));
  public static Invoice invoice8 = new Invoice(8L, "2020/03/08/0000008", LocalDate.of(2022, 5, 13), company5, company2, List.of(invoiceEntry6));

  public static Company company(Long id) {
    return Company.builder()
        .id(id)
        .taxIdentificationNumber(String.valueOf(id))
        .address("Polna " + id + ", 80-300 Katowice")
        .name("Majkrosoft " + id)
        .healthInsurance(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(id)).setScale(2))
        .pensionInsurance(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(id)).setScale(2))
        .build();
  }

  public static InvoiceEntry product(Long id) {
    return InvoiceEntry.builder().description("Windows " + id)
        .id(id)
        .quantity(1)
        .netPrice(BigDecimal.valueOf(id * 100L).setScale(2))
        .vatValue(BigDecimal.valueOf(100 * 0.08).setScale(2))
        .vatRate(Vat.VAT_8)
        .carExpenseIsRelatedTo(Car.builder().registrationNumber("GD88822").isUsedForPersonalPurpose(false).build())
        .build();
  }

  public static Invoice invoice(Long id) {
    return Invoice.builder()
        .id(id)
        .date(LocalDate.now())
        .number("111/2222/33344/$id")
        .seller(company(id + 1))
        .buyer(company(id))
        .invoiceEntries(List.of(product(id)))
        .build();
  }

}
