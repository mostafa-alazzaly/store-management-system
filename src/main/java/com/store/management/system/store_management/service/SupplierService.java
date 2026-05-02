package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.supplier.request.CreateSupplierRequest;
import com.store.management.system.store_management.dto.supplier.response.SupplierResponse;
import com.store.management.system.store_management.dto.supplier.request.UpdateSupplierRequest;
import com.store.management.system.store_management.entity.ProductSupplier;
import com.store.management.system.store_management.entity.Supplier;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.ProductSupplierRepo;
import com.store.management.system.store_management.repo.SupplierRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierService {
    private  final SupplierRepo supplierRepo;
    private  final ProductSupplierRepo productSupplierRepo;

    public SupplierService(SupplierRepo supplierRepo , ProductSupplierRepo productSupplierRepo) {
        this.supplierRepo = supplierRepo;
        this.productSupplierRepo = productSupplierRepo;
    }
    private SupplierResponse mapToResponse(Supplier supplier){
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setId(supplier.getId());
        supplierResponse.setSupplierCode(supplier.getSupplierCode());
        supplierResponse.setCompanyName(supplier.getCompanyName());
        supplierResponse.setCompanyPhone(supplier.getCompanyPhone());
        supplierResponse.setContactPersonName(supplier.getContactPersonName());
        supplierResponse.setContactPersonPhone(supplier.getContactPersonPhone());
        supplierResponse.setEmail(supplier.getEmail());
        supplierResponse.setAddress(supplier.getAddress());
        supplierResponse.setCity(supplier.getCity());
        supplierResponse.setCountry(supplier.getCountry());
        supplierResponse.setZipCode(supplier.getZipCode());
        supplierResponse.setWebsite(supplier.getWebsite());
        supplierResponse.setTaxNumber(supplier.getTaxNumber());
        supplierResponse.setNotes(supplier.getNotes());
        supplierResponse.setPaymentTerms(supplier.getPaymentTerms());
        supplierResponse.setStatus(supplier.getStatus());
        return supplierResponse;
    }
    public Page<SupplierResponse> getAllSuppliers(Pageable pageable){
        return supplierRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<SupplierResponse> getActiveSuppliers(Pageable pageable){
        return supplierRepo.findByStatus(Supplier.Status.ACTIVE,pageable)
                .map(this::mapToResponse);
    }

    public SupplierResponse getSupplierById(Integer id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth != null && auth.getAuthorities()
                            .stream()
                            .anyMatch(a->"ROLE_ADMIN".equals(a.getAuthority()));
        Supplier supplier;
        if(isAdmin){
            supplier = supplierRepo.findById(id)
                    .orElseThrow (()-> new SupplierNotFoundException("Supplier not found with id " + id));
        }else{
            supplier= supplierRepo.findByIdAndStatus(id,Supplier.Status.ACTIVE)
                    .orElseThrow (()-> new SupplierNotFoundException("Supplier not found with id " + id));

        }
        return mapToResponse(supplier);
    }

    @Transactional
    public SupplierResponse createSupplier (CreateSupplierRequest request){
        if (supplierRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email Already Exist: " + request.getEmail()) ;
        }
        if (request.getTaxNumber() != null &&
                !request.getTaxNumber().isBlank() &&
                supplierRepo.existsByTaxNumber(request.getTaxNumber())) {
            throw new TaxNumberAlreadyExistsException("Tax Number Already Exist: " + request.getTaxNumber()) ;
        }
        Supplier  supplier = new Supplier();
        supplier.setCompanyName(request.getCompanyName());
        supplier.setCompanyPhone(request.getCompanyPhone());
        supplier.setContactPersonName(request.getContactPersonName());
        supplier.setContactPersonPhone(
                request.getContactPersonPhone() != null ? request.getContactPersonPhone() : null);
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setCountry(request.getCountry());
        supplier.setZipCode(
                request.getZipCode()  != null ? request.getZipCode() : null);
        supplier.setWebsite(
                request.getWebsite()  != null ? request.getWebsite() : null);
        supplier.setTaxNumber(
                request.getTaxNumber() != null ? request.getTaxNumber() : null );
        supplier.setNotes(
                request.getNotes() != null ? request.getNotes() : null);
        supplier.setPaymentTerms(
                request.getPaymentTerms() != null ? request.getPaymentTerms() : Supplier.PaymentTerms.CASH ) ;
        supplier.setStatus(
                request.getStatus() != null ? request.getStatus() : Supplier.Status.ACTIVE);

        Supplier savedSupplier = supplierRepo.save(supplier);
        savedSupplier.setSupplierCode(
                "SUP-" + String.format("%03d", savedSupplier.getId())
        );
        return mapToResponse(savedSupplier);
    }
    private void updateCompanyName (Supplier supplier,UpdateSupplierRequest request){
        if (request.getCompanyName() != null) {
            supplier.setCompanyName(request.getCompanyName());
        }
    }

    private void updateCompanyPhone (Supplier supplier,UpdateSupplierRequest request){
        if (request.getCompanyPhone() != null) {
            supplier.setCompanyPhone(request.getCompanyPhone());
        }
    }
    private void updateContactPersonName (Supplier supplier,UpdateSupplierRequest request){
        if (request.getContactPersonName() != null) {
            supplier.setContactPersonName(request.getContactPersonName());
        }
    }
    private void updateContactPersonPhone (Supplier supplier,UpdateSupplierRequest request){
        if (request.getContactPersonPhone() != null) {
            supplier.setContactPersonPhone(request.getContactPersonPhone());
        }
    }
    private void updateEmail (Supplier supplier,UpdateSupplierRequest request){
        if(request.getEmail() != null) {

            if (!request.getEmail().equals(supplier.getEmail()) &&
                    supplierRepo.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists");
            }
            supplier.setEmail(request.getEmail());
        }
    }
    private void updateAddress(Supplier supplier,UpdateSupplierRequest request){
        if (request.getAddress() != null) {
            supplier.setAddress(request.getAddress());
        }
    }
    private void updateCountry (Supplier supplier,UpdateSupplierRequest request){
        if (request.getCountry() != null) {
            supplier.setCountry(request.getCountry());
        }
    }
    private void updateCity (Supplier supplier,UpdateSupplierRequest request){
        if (request.getCity() != null) {
            supplier.setCity(request.getCity());
        }
    }
    private void updateZipCode (Supplier supplier,UpdateSupplierRequest request){
        if (request.getZipCode() != null) {
            supplier.setZipCode(request.getZipCode());
        }
    }
    private void updateWebsite (Supplier supplier,UpdateSupplierRequest request){
        if (request.getWebsite() != null) {
            supplier.setWebsite(request.getWebsite());
        }
    }
    private void updateTaxNumber(Supplier supplier,UpdateSupplierRequest request){
        if (request.getTaxNumber() != null){

            if (!request.getTaxNumber().equals(supplier.getTaxNumber()) &&
                supplierRepo.existsByTaxNumber(request.getTaxNumber())) {
                     throw new TaxNumberAlreadyExistsException("Tax number already exists");
        }
        supplier.setTaxNumber(request.getTaxNumber());
        }
    }
    private void updateNotes(Supplier supplier,UpdateSupplierRequest request){
        if (request.getNotes() != null) {
            supplier.setNotes(request.getNotes());
        }
    }
    private void updatePaymentTerms(Supplier supplier,UpdateSupplierRequest request){
        if (request.getPaymentTerms() != null) {
            supplier.setPaymentTerms(request.getPaymentTerms());
        }
    }
    @Transactional
    public SupplierResponse updateSupplier(Integer id , UpdateSupplierRequest request){
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() ->new SupplierNotFoundException("Supplier not found with id: " + id));

        updateCompanyName(supplier,request);
        updateCompanyPhone(supplier,request);
        updateContactPersonName(supplier,request);
        updateContactPersonPhone(supplier,request);
        updateEmail(supplier,request);
        updateAddress(supplier,request);
        updateCountry(supplier,request);
        updateCity(supplier,request);
        updateZipCode(supplier,request);
        updateWebsite(supplier,request);
        updateTaxNumber(supplier,request);
        updateNotes(supplier,request);
        updatePaymentTerms(supplier,request);

        supplierRepo.save(supplier);
        return mapToResponse(supplier);
    }

    @Transactional
    public void deactivateSupplier (Integer id){

        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() ->new SupplierNotFoundException("Supplier not found with id: " + id));

        if (supplier.getStatus() == Supplier.Status.INACTIVE) {
            throw new SupplierAlreadyDeactivatedException("Supplier with id : " + id + " is already deactivated");
        }
        supplier.setStatus(Supplier.Status.INACTIVE);

        if (supplier.getProductSuppliers() != null) {
            for (ProductSupplier ps :  supplier.getProductSuppliers()) {
                ps.setStatus(ProductSupplier.Status.INACTIVE);
                ps.setPrimary(false);
            }
        }

    }
    @Transactional
    public void activateSupplier (Integer id){

        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() ->new SupplierNotFoundException("Supplier not found with id: " + id));

        if (supplier.getStatus() == Supplier.Status.ACTIVE) {
            throw new SupplierAlreadyActivatedException("Supplier with id : " + id + " is already activated");
        }

        supplier.setStatus(Supplier.Status.ACTIVE);
    }

    @Transactional
    public void deleteSupplierById(Integer id){

        Supplier supplier = supplierRepo.findById(id)
             .orElseThrow(() ->new SupplierNotFoundException("Supplier not found with id : " + id));

        if(supplier.getStatus() == Supplier.Status.ACTIVE){
            throw new CannotDeleteActiveSupplierException("Supplier with id : " + id + " is already active");
        }
        productSupplierRepo.deleteBySupplierId(id);
        supplierRepo.delete(supplier);
    }
}
