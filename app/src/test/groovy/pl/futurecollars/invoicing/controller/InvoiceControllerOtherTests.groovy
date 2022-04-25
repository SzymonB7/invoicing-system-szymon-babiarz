package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext

import static pl.futurecollars.invoicing.helpers.TestHelpers.invoice
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InvoiceControllerOtherTests extends ControllerTest {

    def "empty array is returned when no invoices were added"() {

        expect:
        getAllInvoices() == []
    }

    def "add invoice returns correctly id"() {
        given:
        def invoiceAsJson = invoiceAsJson(1)

        expect:
        def id = addOneInvoice(invoiceAsJson)
        addOneInvoice(invoiceAsJson) == id + 1
        addOneInvoice(invoiceAsJson) == id + 2
        addOneInvoice(invoiceAsJson) == id + 3
    }

    def "all invoices are returned when getting all invoices"() {
        given:
        def sumOfInvoices = 5
        def expectedInvoices = addUniqueInvoices(sumOfInvoices)

        expect:
        getAllInvoices().size() == sumOfInvoices
        getAllInvoices() == expectedInvoices
    }

    def "returned invoice when getting correct id"() {
        given:
        def expectedInvoice = addUniqueInvoices(7)
        def verifiedInvoice = expectedInvoice.get(3)

        when:
        def invoice = getInvoiceById(verifiedInvoice.getId())

        then:
        invoice == verifiedInvoice
    }

    def "status 404 is returned when invoice id is not found when getting invoice"() {
        given:
        addUniqueInvoices(11)

        expect:
        mockMvc.perform(get("$INVOICES_ENDPOINT/$id"))
                .andExpect(status().isNotFound())

        where:
        id << [-500, -200, -50, 0, 128, 512, 1024]
    }

    def "can delete invoice"() {
        given:
        def invoices = addUniqueInvoices(3)

        expect:
        invoices.each { invoice -> deleteInvoice(invoice.getId()) }
        getAllInvoices().size() == 0
    }

    def "status 404 is returned when invoice id is not found when deleting invoice"() {
        given:
        addUniqueInvoices(11)

        expect:
        mockMvc.perform(delete("$INVOICES_ENDPOINT/$id"))
                .andExpect(status().isNotFound())

        where:
        id << [-500, -200, -50, 0, 128, 512, 1024]
    }

    def "can update invoice"() {
        given:
        def id = addOneInvoice(invoiceAsJson(10))
        def updatedInvoice = invoice(12)
        updatedInvoice.id = id
        def updatedInvoiceAsJson = jsonService.writeObjectAsJson(updatedInvoice)

        expect:
        mockMvc.perform(put("$INVOICES_ENDPOINT/$id")
                .content(updatedInvoiceAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())


        getInvoiceById(id) == updatedInvoice
    }

    def "returned status 404 when updating not existing invoice"() {
        given:
        addUniqueInvoices(6)

        expect:
        mockMvc.perform(put("$INVOICES_ENDPOINT/$id")
                .content(invoiceAsJson(1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

        where:
        id << [-500, -200, -50, 0, 128, 512, 1024]
    }
}
