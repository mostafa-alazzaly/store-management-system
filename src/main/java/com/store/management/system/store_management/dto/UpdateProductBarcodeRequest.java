package com.store.management.system.store_management.dto;


import com.store.management.system.store_management.validation.NotBlankIfPresent;

public class UpdateProductBarcodeRequest {

    @NotBlankIfPresent(message="Barcode is Can't be empty")
    private String barcode;

    public String getBarcode() {
        return barcode;
    }
}
