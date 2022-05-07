package pl.futurecollars.invoicing.controller
//@AutoConfigureMockMvc
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class TaxCalculatorControllerTest extends Specification {
//
//    @Autowired
//    private MockMvc mockMvc
//    @Autowired
//    private JsonService jsonService
//
//    private Invoice invoice1
//    private Invoice invoice2
//    private Invoice invoice3
//    private Invoice invoice4
//    private Invoice invoice5
//
//    def setup() {
//        invoice1 = TestHelpers.invoice1
//        invoice2 = TestHelpers.invoice2
//        invoice3 = TestHelpers.invoice3
//        invoice4 = TestHelpers.invoice4
//        invoice5 = TestHelpers.invoice5
//    }
//
//    def "helper_post"(Invoice invoice) {
//
//        def invoiceAsJson = jsonService.writeObjectAsJson(invoice)
//
//        def response = mockMvc.perform(post("/invoices").content(invoiceAsJson).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn()
//                .response
//                .contentAsString
//
//        return response
//    }
//
//    def "ComputeTaxes"() {
//
//        given:
//        helper_post(invoice1)
//        helper_post(invoice2)
//        helper_post(invoice3)
//        helper_post(invoice4)
//        helper_post(invoice5)
//        String taxIdNumber = invoice1.getSeller().getTaxIdentificationNumber()
//
//        when:
//
//        def responseTaxes = mockMvc.perform(get("/tax-bill/" + taxIdNumber))
//                .andExpect(status().isOk())
//                .andReturn()
//                .response
//                .getContentAsString()
//
//        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)
//
//        then:
//        taxCalculatorResult.earnings == 600
//        taxCalculatorResult.costs == 300
//        taxCalculatorResult.income == 900
//        taxCalculatorResult.collectedVat == 156
//        taxCalculatorResult.paidVat == 69
//        taxCalculatorResult.vatToPay == 87
//
//    }
//
//    def "should return 0 in each result if not matching taxId was chosen"() {
//
//        given:
//        helper_post(invoice1)
//        helper_post(invoice2)
//        helper_post(invoice3)
//        helper_post(invoice4)
//        helper_post(invoice5)
//        String taxIdNumber = "12345"
//
//        when:
//
//        def responseTaxes = mockMvc.perform(get("/tax-bill/" + taxIdNumber))
//                .andExpect(status().isOk())
//                .andReturn()
//                .response
//                .getContentAsString()
//
//        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)
//
//        then:
//        taxCalculatorResult.earnings == 0
//        taxCalculatorResult.costs == 0
//        taxCalculatorResult.income == 0
//        taxCalculatorResult.collectedVat == 0
//        taxCalculatorResult.paidVat == 0
//        taxCalculatorResult.vatToPay == 0
//
//    }
//
//    def "should return 0 in each result if no invoice was posted "() {
//
//        when:
//        def responseTaxes = mockMvc.perform(get("/tax-bill/11"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .response
//                .getContentAsString()
//
//        TaxBill taxCalculatorResult = jsonService.readJsonAsObject(responseTaxes, TaxBill)
//
//        then:
//        taxCalculatorResult.earnings == 0
//        taxCalculatorResult.costs == 0
//        taxCalculatorResult.income == 0
//        taxCalculatorResult.collectedVat == 0
//        taxCalculatorResult.paidVat == 0
//        taxCalculatorResult.vatToPay == 0
//
//    }
//}
