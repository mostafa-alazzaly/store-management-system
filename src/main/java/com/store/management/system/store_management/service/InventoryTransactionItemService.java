package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.InventoryTransactionItem.response.InventoryTransactionItemResponse;
import com.store.management.system.store_management.entity.InventoryTransactionItem;
import com.store.management.system.store_management.exception.InventoryTransactionItemNotFoundException;
import com.store.management.system.store_management.repo.InventoryTransactionItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryTransactionItemService {

    private final InventoryTransactionItemRepo inventoryTransactionItemRepo;
    @Autowired
    public InventoryTransactionItemService(InventoryTransactionItemRepo inventoryTransactionItemRepo) {
        this.inventoryTransactionItemRepo = inventoryTransactionItemRepo;
    }

    private InventoryTransactionItemResponse mapTOInventoryTransactionItemResponse(InventoryTransactionItem inventoryTransactionItem){
        InventoryTransactionItemResponse inventoryTransactionItemResponse = new InventoryTransactionItemResponse();
        inventoryTransactionItemResponse.setId(inventoryTransactionItem.getId());
        inventoryTransactionItemResponse.setProductId(inventoryTransactionItem.getProduct().getId());
        inventoryTransactionItemResponse.setInventoryTransactionId(inventoryTransactionItem.getInventoryTransaction().getId());
        inventoryTransactionItemResponse.setQuantity(inventoryTransactionItem.getQuantity());
        inventoryTransactionItemResponse.setUnitCost(inventoryTransactionItem.getUnitCost());

        return inventoryTransactionItemResponse;
    }

    public Page<InventoryTransactionItemResponse> getAllInventoryTransactionItem(Pageable pageable){
        return inventoryTransactionItemRepo.findAll(pageable)
                .map(this::mapTOInventoryTransactionItemResponse);

    }

    public InventoryTransactionItemResponse getInventoryTransactionItemById(Integer id){
        InventoryTransactionItem inventoryTransactionItem = inventoryTransactionItemRepo.findById(id)
                .orElseThrow (()-> new InventoryTransactionItemNotFoundException ("Inventory Transaction Item Not Found with id "+id));
        return mapTOInventoryTransactionItemResponse(inventoryTransactionItem);
    }

}
