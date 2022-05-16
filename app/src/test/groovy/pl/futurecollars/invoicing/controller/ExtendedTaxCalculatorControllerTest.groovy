package pl.futurecollars.invoicing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import pl.futurecollars.invoicing.db.file.JsonService
import pl.futurecollars.invoicing.helpers.TestHelpers
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.TaxBill
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExtendedTaxCalculatorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc
    @Autowired
    private JsonService jsonService

    private Invoice invoice1
    private Invoice invoice2
    private Invoice invoice3
    private Invoice invoice4
    private Invoice invoice5
    private Invoice invoice6
    private Invoice invoice7
    private Invoice invoice8


    private Company company

    def setup() {
        invoice1 = TestHelpers.invoice1
        invoice2 = TestHelpers.invoice2
        invoice3 = TestHelpers.invoice3
        invoice4 = TestHelpers.invoice4
        invoice5 = TestHelpers.invoice5
        invoice6 = TestHelpers.invoice6
        invoice7 = TestHelpers.invoice7
        invoice8 = TestHelpers.invoice8
    }

    def "helper_post"(Invoice invoice) {
        def invoiceAsJson = jsonService.writeObjectAsJson(invoice)

        def response = mockMvc.perform(post("/invoices").content(invoiceAsJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        return response
    }

    def "should compute all result for company1 (with insurance 0) and 5 selected invoices in proper way"() {

        given:
        helper_post(invoice1)
        helper_post(invoice2)
        helper_post(invoice3)
        helper_post(invoice4)
        helper_post(invoice5)

        company = TestHelpers.company1

        def companyAsJson = jsonService.writeObjectAsJson(company)

        when:
        def responseTaxes = mockMvc.perform(post("/tax-bill/").content(companyAsJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)

        then:
        taxCalculatorResult.costs == 300
        taxCalculatorResult.income == 900
        taxCalculatorResult.collectedVat == 156
        taxCalculatorResult.paidVat == 69
        taxCalculatorResult.vatToPay == 87
        taxCalculatorResult.incomeMinusCosts == 600
        taxCalculatorResult.pensionInsurance == 0
        taxCalculatorResult.incomeMinusCostsMinusPensionInsurance == 600
        taxCalculatorResult.taxCalculationBase == 600
        taxCalculatorResult.incomeTax == 114
        taxCalculatorResult.healthInsuranceFull == 0
        taxCalculatorResult.healthInsuranceToSubtract == 0
        taxCalculatorResult.incomeTaxMinusHealthInsurance == 114
        taxCalculatorResult.finalIncomeTax == 114
    }


    def "should compute all result for company4 (with insurance 13000) and 3 selected invoices in proper way"() {

        given:
        helper_post(invoice6)
        helper_post(invoice7)
        helper_post(invoice8)

        company = TestHelpers.company4

        def companyAsJson = jsonService.writeObjectAsJson(company)

        when:
        def responseTaxes = mockMvc.perform(post("/tax-bill/").content(companyAsJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)

        then:
        taxCalculatorResult.costs == 0
        taxCalculatorResult.income == 4000
        taxCalculatorResult.collectedVat == 200
        taxCalculatorResult.paidVat == 0
        taxCalculatorResult.vatToPay == 200
        taxCalculatorResult.incomeMinusCosts == 4000
        taxCalculatorResult.pensionInsurance == 13000
        taxCalculatorResult.incomeMinusCostsMinusPensionInsurance == -9000
        taxCalculatorResult.taxCalculationBase == -9000
        taxCalculatorResult.incomeTax == -1710
        taxCalculatorResult.healthInsuranceFull == 0
        taxCalculatorResult.healthInsuranceToSubtract == 0
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -1710
        taxCalculatorResult.finalIncomeTax == -1710
    }

    def "should compute all result for company5 (with insurance 30000) and invoice related to car expenses "() {

        given:
        helper_post(invoice6)
        helper_post(invoice7)
        helper_post(invoice8)

        company = TestHelpers.company5

        def companyAsJson = jsonService.writeObjectAsJson(company)

        when:
        def responseTaxes = mockMvc.perform(post("/tax-bill/").content(companyAsJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)

        then:

        taxCalculatorResult.costs == 6132.5
        taxCalculatorResult.income == 0
        taxCalculatorResult.collectedVat == 0
        taxCalculatorResult.paidVat == 632.50
        taxCalculatorResult.vatToPay == -632.50
        taxCalculatorResult.incomeMinusCosts == -6132.5
        taxCalculatorResult.pensionInsurance == 30000
        taxCalculatorResult.incomeMinusCostsMinusPensionInsurance == -36132.5
        taxCalculatorResult.taxCalculationBase == -36132
        taxCalculatorResult.incomeTax == -6865.08
        taxCalculatorResult.healthInsuranceFull == 0
        taxCalculatorResult.healthInsuranceToSubtract == 0
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -6865.08
        taxCalculatorResult.finalIncomeTax == -6865
    }

}