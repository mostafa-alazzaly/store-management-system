package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.inventoryTransaction.request.CreateInventoryTransactionRequest;
import com.store.management.system.store_management.dto.inventoryTransaction.response.InventoryTransactionResponse;
import com.store.management.system.store_management.service.InventoryTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;
    @Autowired
    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

   @PostMapping
    public InventoryTransactionResponse createInventoryTransaction( @Valid @RequestBody CreateInventoryTransactionRequest inventoryTransaction){
        return inventoryTransactionService.createInventoryTransaction(inventoryTransaction);
   }

   @GetMapping
   public Page<InventoryTransactionResponse> getAllInventoryTransaction (Pageable pageable  ){
        return inventoryTransactionService.getAllInventoryTransactions(pageable);
   }
    @GetMapping("/{id}")
    public InventoryTransactionResponse getInventoryTransactionById(@PathVariable Integer id){
        return inventoryTransactionService.getInventoryTransactionById(id);
    }
    @PostMapping("/{id}/reverse")
    public InventoryTransactionResponse reverseInventoryTransaction(@PathVariable Integer id) {
        return inventoryTransactionService.reverseInventoryTransaction(id);
    }
}
