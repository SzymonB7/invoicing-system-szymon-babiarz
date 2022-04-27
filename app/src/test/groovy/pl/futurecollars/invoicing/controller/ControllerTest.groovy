package pl.futurecollars.invoicing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import pl.futurecollars.invoicing.db.file.JsonService
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.TaxBill
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static pl.futurecollars.invoicing.helpers.TestHelpers.invoice

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest extends Specification {

    static final String INVOICES_ENDPOINT = "/invoices"
    static final String TAXES_ENDPOINT = "/tax-bill"

    @Autowired
    MockMvc mockMvc

    @Autowired
    JsonService jsonService

   def setup() {
       getAllInvoices().each {
           invoice -> deleteInvoice(invoice.id)
       }
   }

    int addOneInvoice(String invoiceAsJson) {
        Integer.valueOf(mockMvc.perform(post(INVOICES_ENDPOINT)
                                .content(invoiceAsJson)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .response
                        .contentAsString
        )
    }

    List<Invoice> getAllInvoices() {
        def response = mockMvc.perform(get(INVOICES_ENDPOINT))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        jsonService.readJsonAsObject(response, Invoice[])
    }

    protected  List<Invoice> addUniqueInvoices(int count) {
        (1..count).collect { id ->
            def invoice = invoice(id)
            invoice.id = addOneInvoice(jsonService.writeObjectAsJson(invoice))
            return invoice
        }
    }

    void deleteInvoice(int id) {
        mockMvc.perform(delete("$INVOICES_ENDPOINT/$id"))
                .andExpect(status().isNoContent())
    }

    Invoice getInvoiceById(int id) {
        def invoiceAsString = mockMvc.perform(get("$INVOICES_ENDPOINT/$id"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        jsonService.readJsonAsObject(invoiceAsString, Invoice)
    }

    String invoiceAsJson(int id) {
        jsonService.writeObjectAsJson(invoice(id))
    }

    TaxBill calculateTax(String taxIdentificationNumber) {
        def response = mockMvc.perform(get("$TAXES_ENDPOINT/$taxIdentificationNumber"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        jsonService.readJsonAsObject(response, TaxBill)
    }

}
