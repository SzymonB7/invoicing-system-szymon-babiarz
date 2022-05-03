package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase
import pl.futurecollars.invoicing.helpers.TestHelpers
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import spock.lang.Specification
import java.util.function.Function

class TaxCalculatorServiceTest extends Specification {

    private Database database
    private TaxCalculatorService taxCalculatorService
    private Invoice invoice1
    private Invoice invoice2
    private Invoice invoice3
    private Invoice invoice4
    private Invoice invoice5

    void setup() {

        database = new InMemoryDatabase()
        taxCalculatorService = new TaxCalculatorService(database)
        invoice1 = TestHelpers.invoice1
        invoice2 = TestHelpers.invoice2
        invoice3 = TestHelpers.invoice3
        invoice4 = TestHelpers.invoice4
        invoice5 = TestHelpers.invoice5
    }


    def "helper_method"(Function<InvoiceEntry, BigDecimal> mapper) {

        def expectedIncome1 = invoice1.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        def expectedIncome2 = invoice2.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        def expectedIncome3 = invoice3.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        BigDecimal expectedResult = expectedIncome1 + expectedIncome2 + expectedIncome3

        return expectedResult
    }


    def "income method should return correct income from 3 invoices with the same seller"() {

        given:
        invoice2.setSeller(invoice1.getSeller())
        invoice3.setSeller(invoice1.getSeller())
        database.save(invoice1)
        database.save(invoice2)
        database.save(invoice3)

        Function<InvoiceEntry, BigDecimal> incomeMapper = InvoiceEntry -> InvoiceEntry.getNetPrice()
        def expectedResult = helper_method(incomeMapper)

        when:
        def incomeTaxCalculator = taxCalculatorService.calculateIncome(invoice1.getSeller().getTaxIdentificationNumber())

        then:
        expectedResult == incomeTaxCalculator
    }


    def "cost method should return correct cost from 3 invoices with the same buyer"() {

        given:
        invoice2.setBuyer(invoice1.getBuyer())
        invoice3.setBuyer(invoice1.getBuyer())
        database.save(invoice1)
        database.save(invoice2)
        database.save(invoice3)

        Function<InvoiceEntry, BigDecimal> costMapper = InvoiceEntry -> InvoiceEntry.getNetPrice()
        def expectedResult = helper_method(costMapper)

        when:
        def costTaxCalculator = taxCalculatorService.calculateCosts(invoice1.getBuyer().getTaxIdentificationNumber())

        then:
        expectedResult == costTaxCalculator

    }

    def "IncomingVat"() {

        given:
        invoice2.setSeller(invoice1.getSeller())
        invoice3.setSeller(invoice1.getSeller())
        database.save(invoice1)
        database.save(invoice2)
        database.save(invoice3)

        Function<InvoiceEntry, BigDecimal> incomingVatMapper = InvoiceEntry -> InvoiceEntry.getVatValue()
        def expectedResult = helper_method(incomingVatMapper)

        when:
        def incomeVatTaxCalculator = taxCalculatorService.calculateCollectedVat(invoice1.getSeller().getTaxIdentificationNumber())

        then:
        expectedResult == incomeVatTaxCalculator

    }

    def "OutgoingVat method should return correct vat"() {

        given:
        invoice2.setBuyer(invoice1.getBuyer())
        invoice3.setBuyer(invoice1.getBuyer())
        database.save(invoice1)
        database.save(invoice2)
        database.save(invoice3)

        Function<InvoiceEntry, BigDecimal> outgoingVatMapper = InvoiceEntry -> InvoiceEntry.getVatValue()
        def expectedResult = helper_method(outgoingVatMapper)

        when:
        def outgoingVatTaxCalculator = taxCalculatorService.calculatePaidVat(invoice1.getBuyer().getTaxIdentificationNumber())

        then:
        expectedResult == outgoingVatTaxCalculator
    }

    def "earning method should return correct earning from 2 different invoices"() {

        given:
        database.save(invoice4)
        database.save(invoice5)

        Function<InvoiceEntry, BigDecimal> mapper = InvoiceEntry -> InvoiceEntry.getNetPrice()


        def expectedIncome = invoice4.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        def expectedCost = invoice5.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        def expectedResult = expectedIncome - expectedCost

        when:
        def earningsTaxCalculator = taxCalculatorService.calculateEarnings("5")

        then:
        expectedResult == earningsTaxCalculator

    }

    def "VatToPay method should return correct vat to pay/return "() {

        given:
        database.save(invoice4)
        database.save(invoice5)

        Function<InvoiceEntry, BigDecimal> mapper = InvoiceEntry -> InvoiceEntry.getVatValue()

        def expectedIncomingVat = invoice4.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b)

        def expectedOutgoingVat = invoice5.getInvoiceEntries().stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, (a, b) -> a + b);

        def expectedResult = expectedIncomingVat - expectedOutgoingVat

        when:
        def vatToReturnOrPayTaxCalculator = taxCalculatorService.calculateVatToPay("11")

        then:
        expectedResult == vatToReturnOrPayTaxCalculator

    }

}
