package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

  private final InvoiceService invoiceService = new InvoiceService(new InMemoryDatabase());


  @PostMapping
  public Integer save(@RequestBody Invoice invoice) {
    return invoiceService.save(invoice);
  }

  @GetMapping
  public List<Invoice> getAll() {
    return invoiceService.getAll();

  }

  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getById (@PathVariable Integer id) {
    return invoiceService.getById(id)
        .map(invoice -> ResponseEntity.ok().body(invoice))
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping
  public ResponseEntity<?> update (Integer id, Invoice updatedInvoice){
    try{
      invoiceService.update(id, updatedInvoice);
      return ResponseEntity.ok().build();
    } catch (RuntimeException exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping
  public ResponseEntity<?> delete (Integer id) {
    try{
      invoiceService.delete(id);
      return ResponseEntity.ok().build();
    } catch (RuntimeException exception) {
      return ResponseEntity.notFound().build();
    }
  }
}