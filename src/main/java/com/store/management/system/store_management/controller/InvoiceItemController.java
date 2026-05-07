package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.invoiceItem.response.InvoiceItemResponse;
import com.store.management.system.store_management.service.InvoiceItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-items")
public class InvoiceItemController {


    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping
    public Page<InvoiceItemResponse> getAllInvoiceItems(
            @RequestParam(required=false) Integer invoiceId,
            @RequestParam(required=false) Integer productId,
            Pageable pageable
    ) {
        return invoiceItemService.getAllInvoiceItem(invoiceId, productId, pageable);
    }

    @GetMapping("/{id}")
    public InvoiceItemResponse getInvoiceItemById(@PathVariable Integer id)
    {
        return invoiceItemService.getInvoiceItemById(id);
    }
}
