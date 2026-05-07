package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.invoice.request.CreateInvoiceRequest;
import com.store.management.system.store_management.dto.invoice.request.UpdateInvoiceRequest;
import com.store.management.system.store_management.dto.invoice.response.InvoiceResponse;
import com.store.management.system.store_management.entity.Invoice;
import com.store.management.system.store_management.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/invoices")
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public InvoiceResponse createInvoice (@Valid @RequestBody CreateInvoiceRequest request){
        return invoiceService.createInvoice(request);
    }
    @PostMapping("/{id}/post")
    public InvoiceResponse postInvoice (@PathVariable Integer id){
        return invoiceService.postInvoice(id);
    }
    @PostMapping("/{id}/cancel")
    public InvoiceResponse cancelPostedInvoice (@PathVariable Integer id){
        return invoiceService.cancelPostedInvoice(id);
    }
    @GetMapping
    public Page<InvoiceResponse> getAllInvoices(
            @RequestParam(required=false) Invoice.InvoiceStatus status,
            @RequestParam(required=false) Invoice.InvoiceType type,
            @RequestParam(required=false) Integer customerId,
            @RequestParam(required=false) Integer supplierId,
            Pageable pageable){
        return invoiceService.getAllInvoices(status, type, customerId, supplierId,pageable);
    }
    @GetMapping("/{id}")
    public InvoiceResponse getInvoice(@PathVariable Integer id){
        return invoiceService.getInvoice(id);
    }
    @PatchMapping("/{id}")
    public InvoiceResponse updateDraftInvoice(@PathVariable Integer id,  @RequestBody UpdateInvoiceRequest request){
        return invoiceService.updateDraftInvoice(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteDraftInvoice(@PathVariable Integer id){
        invoiceService.deleteDraftInvoice(id);
        return "Draft invoice deleted successfully with id : " + id ;
    }
    @DeleteMapping("/{invoiceId}/items/{invoiceItemId}")
    public InvoiceResponse deleteDraftItem(@PathVariable Integer invoiceId, @PathVariable Integer invoiceItemId){
        return invoiceService.deleteDraftItem(invoiceId, invoiceItemId);
    }

}
