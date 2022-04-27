package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureMockMvc
@SpringBootTest
class TaxCalculatorControllerTest extends ControllerTest {

    def "zeros are returned when no invoices were created"() {
        when:
        def response = calculateTax("0")

        then:
        response.income == 0
        response.costs == 0
        response.incomingVat == 0
        response.outgoingVat == 0
        response.earnings == 0
        response.vatToPay == 0
    }

    def "zeros are returned when taxIdentificationNumber not exist in database"() {
        given:
        addUniqueInvoices(5)

        when:
        def response = calculateTax("incorrect_tax_identification_number")

        then:
        response.income == 0
        response.costs == 0
        response.incomingVat == 0
        response.outgoingVat == 0
        response.earnings == 0
        response.vatToPay == 0
    }

    def "should return correct seller values"() {
        given:
        addUniqueInvoices(8)

        when:
        def response = calculateTax("5")

        then:
        response.income == 1500
        response.costs == 0
        response.incomingVat == 120.0
        response.outgoingVat == 0
        response.earnings == 1500
        response.vatToPay == 120.0

        when:
        addUniqueInvoices(8)
        response = calculateTax("5")

        then:
        response.income == 3000
        response.costs == 0
        response.incomingVat == 240.0
        response.outgoingVat == 0
        response.earnings == 3000
        response.vatToPay == 240.0

    }

    def "should return correct buyer values"() {
        given:
        addUniqueInvoices(8)

        when:
        def response = calculateTax("13")

        then:
        response.income == 0
        response.costs == 600
        response.incomingVat == 0
        response.outgoingVat == 48.0
        response.earnings == -600
        response.vatToPay == -48.0

        when:
        addUniqueInvoices(8)
        response = calculateTax("13")

        then:
        response.income == 0
        response.costs == 1200
        response.incomingVat == 0
        response.outgoingVat == 96.0
        response.earnings == -1200
        response.vatToPay == -96.0
    }

    def "should return correct seller and buyer values"() {
        given:
        addUniqueInvoices(23)

        when:
        def response = calculateTax("15")

        then:
        response.income == 12000
        response.costs == 1500
        response.incomingVat == 960.0
        response.outgoingVat == 120.0
        response.earnings == 10500
        response.vatToPay == 840.0
    }
}
