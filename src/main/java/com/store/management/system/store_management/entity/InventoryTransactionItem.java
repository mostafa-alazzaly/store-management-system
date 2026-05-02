package com.store.management.system.store_management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="inventory_transaction_items")
public class InventoryTransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    private Product product ;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="inventory_transaction_id", nullable=false)
    private InventoryTransaction inventoryTransaction;

    @Column(name="quantity", nullable=false)
    private Integer quantity;

    @Column(name="unit_cost", nullable=false)
    private BigDecimal unitCost;

    public InventoryTransactionItem() {}

    public Integer getId() {
        return id;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public InventoryTransaction getInventoryTransaction() {
        return inventoryTransaction;
    }

    public void setInventoryTransaction(InventoryTransaction inventoryTransaction) {
        this.inventoryTransaction = inventoryTransaction;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public String toString() {
        return "InventoryTransactionItem{" +
                "id=" + id +
                ", products=" + product +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                ", inventoryTransaction=" + inventoryTransaction +
                '}';
    }
}
