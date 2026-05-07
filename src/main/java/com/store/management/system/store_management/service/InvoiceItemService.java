package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.invoiceItem.response.InvoiceItemResponse;
import com.store.management.system.store_management.entity.Employee;
import com.store.management.system.store_management.entity.InvoiceItem;
import com.store.management.system.store_management.exception.AccessDeniedException;
import com.store.management.system.store_management.exception.EmployeeNotFoundException;
import com.store.management.system.store_management.exception.InvoiceItemNotFoundException;
import com.store.management.system.store_management.repo.EmployeeRepo;
import com.store.management.system.store_management.repo.InvoiceItemRepo;
import com.store.management.system.store_management.specification.InvoiceItemSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InvoiceItemService {


    private  final InvoiceItemRepo  invoiceItemRepo;
    private  final EmployeeRepo employeeRepo;

    public InvoiceItemService(InvoiceItemRepo  invoiceItemRepo, EmployeeRepo employeeRepo) {
        this.invoiceItemRepo = invoiceItemRepo;
        this.employeeRepo = employeeRepo;
    }

    private InvoiceItemResponse mapToResponse(InvoiceItem invoiceItem){
        InvoiceItemResponse invoiceItemResponse = new InvoiceItemResponse();

        invoiceItemResponse.setId(invoiceItem.getId());
        invoiceItemResponse.setInvoiceId(invoiceItem.getInvoice().getId());
        invoiceItemResponse.setProductId(invoiceItem.getProduct().getId());
        invoiceItemResponse.setQuantity(invoiceItem.getQuantity());
        invoiceItemResponse.setUnitPrice(invoiceItem.getUnitPrice());
        invoiceItemResponse.setDiscount(invoiceItem.getDiscount());
        invoiceItemResponse.setSubTotal(invoiceItem.getSubTotal());
        invoiceItemResponse.setCreatedAt(invoiceItem.getCreatedAt());
        invoiceItemResponse.setUpdatedAt(invoiceItem.getUpdatedAt());


        return invoiceItemResponse;

    }
    private Authentication getAuth(){
        return  SecurityContextHolder.getContext().getAuthentication();
    }
    private boolean hasAnyRole(String... roles) {
        Authentication auth = getAuth();
        if (auth == null) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> {
                    String authority = a.getAuthority();
                    for (String role : roles) {
                        if (("ROLE_" + role).equals(authority))
                            return true;
                    }
                    return false;
                });
    }
    private Employee getCurrentEmployee() {

        String username = getAuth().getName();

        return employeeRepo.findByUserUsername(username)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee profile not found"));
    }

    public Page<InvoiceItemResponse> getAllInvoiceItem(
            Integer invoiceId ,
            Integer productId ,
            Pageable pageable){

        Integer employeeId = null ;

        if (!hasAnyRole("ADMIN","MANAGER")){
            Employee  employee = getCurrentEmployee();
            employeeId =employee.getId();
        }

        Specification<InvoiceItem>  spec = InvoiceItemSpecification.filter(invoiceId,productId,employeeId);

        return  invoiceItemRepo.findAll(spec,pageable)
                .map(this::mapToResponse);
    }

    public InvoiceItemResponse getInvoiceItemById(Integer id){
        InvoiceItem invoiceItem = invoiceItemRepo.findById(id)
                .orElseThrow(() -> new InvoiceItemNotFoundException("Invoice item not found with id " + id));

        if(!hasAnyRole("ADMIN","MANAGER")){
            Employee  employee = getCurrentEmployee();
            if (!invoiceItem.getInvoice().getEmployee().getId().equals(employee.getId())){
                throw new AccessDeniedException("You are not allowed to access this invoice item");
            }
        }
        return mapToResponse(invoiceItem);
    }
}
