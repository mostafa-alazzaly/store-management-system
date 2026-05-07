package com.store.management.system.store_management.specification;

import com.store.management.system.store_management.entity.Payment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {

    public static Specification<Payment> filter(
            Integer invoiceId,
            Integer employeeId,
            Payment.PaymentMethod paymentMethod,
            Payment.PaymentStatus paymentStatus,
            String referenceNumber,
            LocalDate paymentDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(invoiceId != null){
                predicates.add(cb.equal(root.get("invoice").get("id"), invoiceId));
            }
            if(employeeId != null){
                predicates.add(cb.equal(root.get("employee").get("id"), employeeId));
            }
            if(paymentMethod != null){
                predicates.add(cb.equal(root.get("paymentMethod"), paymentMethod));
            }
            if(paymentStatus != null){
                predicates.add(cb.equal(root.get("paymentStatus"), paymentStatus));
            }
            if(referenceNumber != null){
                predicates.add(cb.equal(root.get("referenceNumber"), referenceNumber));
            }
            if(paymentDate!= null){
                predicates.add(cb.equal(root.get("paymentDate"), paymentDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
