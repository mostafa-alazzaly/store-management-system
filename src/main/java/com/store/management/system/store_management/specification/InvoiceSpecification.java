package com.store.management.system.store_management.specification;

import com.store.management.system.store_management.entity.Invoice;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InvoiceSpecification {


    public static Specification<Invoice> filter (
            Invoice.InvoiceStatus status,
            Invoice.InvoiceType type ,
            Integer customerId,
            Integer supplierId
    ) {

        return (root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if(status != null){
                predicates.add(cb.equal(root.get("invoiceStatus"), status));
            }
            if(type != null){
                predicates.add(cb.equal(root.get("invoiceType"), type));
            }
            if(customerId != null){
                predicates.add(cb.equal(root.get("customer").get("id"), customerId));
            }
            if(supplierId != null){
                predicates.add(cb.equal(root.get("supplier").get("id"), supplierId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
