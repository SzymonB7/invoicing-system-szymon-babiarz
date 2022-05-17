package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase
import pl.futurecollars.invoicing.helpers.TestHelpers
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

class ExtendedTaxCalculatorServiceTest extends Specification {

    Database database
    TaxCalculatorService taxCalculatorService
    private Invoice testInvoice1
    private Invoice testInvoice2
    private Invoice testInvoice3

    def setup(){

        database = new InMemoryDatabase()
        taxCalculatorService = new TaxCalculatorService(database)

        testInvoice1 = TestHelpers.invoice6
        testInvoice2 = TestHelpers.invoice7
        testInvoice3 = TestHelpers.invoice8
    }

    def "should return proper result of method CalculateCompanyCostsConsideringPersonalCarUsage"() {
        when:
        BigDecimal actualResult = taxCalculatorService.calculateCompanyCostsConsideringPersonalCarUsage(TestHelpers.invoiceEntry6)
        then:
        actualResult == 6132.5
    }

    def "should calculate proper costs from invoice related to car expenses"() {
        given:
        database.save(testInvoice1)
        database.save(testInvoice2)
        database.save(testInvoice3)

        when:
        BigDecimal actualResult = taxCalculatorService.calculateCosts("55")

        then:
        actualResult == 6132.5

    }
    def "should calculate proper VAT value from invoice related to car expenses"() {

        when:
        BigDecimal actualResult = taxCalculatorService.calculateVatValue(TestHelpers.invoiceEntry5)

        then:
        actualResult == 100
    }
}
