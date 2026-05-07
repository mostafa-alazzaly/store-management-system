package com.store.management.system.store_management.specification;

import com.store.management.system.store_management.entity.InvoiceItem;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InvoiceItemSpecification {

    public static Specification<InvoiceItem> filter(
            Integer invoiceId ,
            Integer productId ,
            Integer employeeId){

        return(root ,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if (invoiceId != null ){
                predicates.add(cb.equal(root.get("invoice").get("id"),invoiceId));
            }
            if (productId != null ){
                predicates.add(cb.equal(root.get("product").get("id"),productId));
            }
            if (employeeId != null) {
                predicates.add(
                        cb.equal(
                                root.get("invoice").get("employee").get("id"),
                                employeeId
                        )
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
