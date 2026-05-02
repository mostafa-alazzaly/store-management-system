package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.InventoryTransactionItem.response.InventoryTransactionItemResponse;
import com.store.management.system.store_management.service.InventoryTransactionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory-transaction-items")
public class InventoryTransactionItemController {


    private final InventoryTransactionItemService inventoryTransactionItemService;

    @Autowired
    public InventoryTransactionItemController(InventoryTransactionItemService inventoryTransactionItemService) {
        this.inventoryTransactionItemService = inventoryTransactionItemService;
    }

    @GetMapping
    public Page<InventoryTransactionItemResponse> getAllInventoryTransactionItem(Pageable pageable){
       return inventoryTransactionItemService.getAllInventoryTransactionItem(pageable);
    }

    @GetMapping("/{id}")
    public InventoryTransactionItemResponse getInventoryTransactionItemById(@PathVariable Integer id){
        return inventoryTransactionItemService.getInventoryTransactionItemById(id);
    }

}
