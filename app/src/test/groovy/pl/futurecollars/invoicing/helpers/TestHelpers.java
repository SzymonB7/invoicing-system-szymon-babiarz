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

  public static Company company1 = new Company(1, "Pampers", "11", "Koncertowa 6/8, 80-301 Warszawa", BigDecimal.ZERO, BigDecimal.ZERO);
  public static Company company2 = new Company(2, "Mars", "22", "Slowackiego 6/8, 80-302 Katowice", BigDecimal.ZERO, BigDecimal.ZERO);
  public static Company company3 = new Company(3, "Apple", "33", "Czarnogorska 6/8, 80-303 Gdansk", BigDecimal.ZERO, BigDecimal.ZERO);
  public static Company company4 = new Company(4, "NewCompany1", "44", "Nowa 6/8, 80-303 Gdansk", BigDecimal.valueOf(13000), BigDecimal.ZERO);
  public static Company company5 = new Company(5, "NewCompany2", "55", "Stara 6/8, 80-303 Gdansk", BigDecimal.valueOf(30000), BigDecimal.ZERO);

  public static Car car1 = new Car(1, "DW123CT", true);
  public static Car car2 = new Car(2, "DW122FC", true);
  public static Car car3 = new Car(3, "DW345JK", true);

  public static InvoiceEntry invoiceEntry1 =
      new InvoiceEntry(1, "Baton", 1, BigDecimal.valueOf(100), BigDecimal.valueOf(100 * 0.08), Vat.VAT_8, null);
  public static InvoiceEntry invoiceEntry2 =
      new InvoiceEntry(2, "Chleb", 1, BigDecimal.valueOf(200), BigDecimal.valueOf(200 * 0.05), Vat.VAT_5, null);
  public static InvoiceEntry invoiceEntry3 =
      new InvoiceEntry(3, "Kakao", 1, BigDecimal.valueOf(300), BigDecimal.valueOf(300 * 0.23), Vat.VAT_23, null);
  public static InvoiceEntry invoiceEntry4 = new InvoiceEntry(4, "fuel", 1, BigDecimal.valueOf(123), BigDecimal.valueOf(123 * 0.08), Vat.VAT_8, car1);
  public static InvoiceEntry invoiceEntry5 =
      new InvoiceEntry(5, "fuel", 1, BigDecimal.valueOf(4000), BigDecimal.valueOf(4000 * 0.05), Vat.VAT_5, car2);
  public static InvoiceEntry invoiceEntry6 =
      new InvoiceEntry(6, "fuel", 1, BigDecimal.valueOf(5500), BigDecimal.valueOf(5500 * 0.23), Vat.VAT_23, car3);

  public static Invoice invoice1 =
      new Invoice(1, "2020/03/08/0000001", LocalDate.of(2020, 10, 11), company1, company2, List.of(invoiceEntry1, invoiceEntry2, invoiceEntry3));
  public static Invoice invoice2 =
      new Invoice(2, "2020/03/08/0000002", LocalDate.of(2019, 11, 15), company3, company2, List.of(invoiceEntry1, invoiceEntry2, invoiceEntry3));
  public static Invoice invoice3 =
      new Invoice(3, "2020/03/08/0000003", LocalDate.of(2020, 12, 13), company2, company3, List.of(invoiceEntry1, invoiceEntry2));
  public static Invoice invoice4 = new Invoice(4, "2020/03/08/0000004", LocalDate.of(2020, 12, 13), company1, company3, List.of(invoiceEntry3));
  public static Invoice invoice5 = new Invoice(5, "2020/03/08/0000005", LocalDate.of(2020, 12, 13), company2, company1, List.of(invoiceEntry3));

  public static Invoice invoice6 =
      new Invoice(6, "2020/03/08/0000006", LocalDate.of(2022, 5, 10), company2, company3, List.of(invoiceEntry4, invoiceEntry5));
  public static Invoice invoice7 = new Invoice(7, "2020/03/08/0000007", LocalDate.of(2022, 5, 12), company4, company2, List.of(invoiceEntry5));
  public static Invoice invoice8 = new Invoice(8, "2020/03/08/0000008", LocalDate.of(2022, 5, 13), company2, company5, List.of(invoiceEntry6));

  public static Company company(int id) {
    return Company.builder()
        .taxIdentificationNumber(String.valueOf(id))
        .address("Polna " + id + ", 80-300 Katowice")
        .name("Majkrosoft " + id)
        .healthInsurance(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(id)))
        .pensionInsurance(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(id)))
        .build();
  }

  public static InvoiceEntry product(int id) {
    return InvoiceEntry.builder().description("Windows " + id)
        .quantity(1)
        .netPrice(BigDecimal.valueOf(id * 100L))
        .vatValue(BigDecimal.valueOf(100 * 0.08))
        .vatRate(Vat.VAT_8)
        .build();
  }

  public static Invoice invoice(int id) {
    return Invoice.builder()
        .date(LocalDate.now())
        .number("111/2222/33344/$id")
        .seller(company(id))
        .buyer(company(id + 1))
        .invoiceEntries(List.of(product(id)))
        .build();
  }

}
