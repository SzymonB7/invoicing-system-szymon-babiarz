package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import spock.lang.Unroll

@Unroll
@AutoConfigureMockMvc
class TaxCalculatorControllerTest extends ControllerTest {

    def "zeros are returned when no invoices were created"() {
        when:
        def taxCalculatorResponse = calculateTax("0")

        then:
        taxCalculatorResponse.income == 0
        taxCalculatorResponse.costs == 0
        taxCalculatorResponse.incomingVat == 0
        taxCalculatorResponse.outgoingVat == 0
        taxCalculatorResponse.earnings == 0
        taxCalculatorResponse.vatToPay == 0
    }

    def "zeros are returned when taxIdNumber not exist in database"() {
        given:
        addUniqueInvoices(5)

        when:
        def taxCalculatorResponse = calculateTax("incorrect_tax_id_num")

        then:
        taxCalculatorResponse.income == 0
        taxCalculatorResponse.costs == 0
        taxCalculatorResponse.incomingVat == 0
        taxCalculatorResponse.outgoingVat == 0
        taxCalculatorResponse.earnings == 0
        taxCalculatorResponse.vatToPay == 0
    }

    def "correct values are returned when providing correct taxIdNumber"() {
        given:
        addUniqueInvoices(15)

        when:
        def taxCalculatorResponse = calculateTax("9")

        then:
        taxCalculatorResponse.income == 45000
        taxCalculatorResponse.costs == 0
        taxCalculatorResponse.incomingVat == 3600.0
        taxCalculatorResponse.outgoingVat == 0
        taxCalculatorResponse.earnings == 45000
        taxCalculatorResponse.vatToPay == 3600.0

        when:
        taxCalculatorResponse = calculateTax("16")

        then:
        taxCalculatorResponse.income == 0
        taxCalculatorResponse.costs == 21000
        taxCalculatorResponse.incomingVat == 0
        taxCalculatorResponse.outgoingVat == 1680.0
        taxCalculatorResponse.earnings == -21000
        taxCalculatorResponse.vatToPay == -1680.0

        when:
        taxCalculatorResponse = calculateTax("11")

        then:
        taxCalculatorResponse.income == 66000
        taxCalculatorResponse.costs == 1000
        taxCalculatorResponse.incomingVat == 5280.0
        taxCalculatorResponse.outgoingVat == 80.0
        taxCalculatorResponse.earnings == 65000
        taxCalculatorResponse.vatToPay == 5200.0
    }
}
