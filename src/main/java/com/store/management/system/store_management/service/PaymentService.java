package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.payment.request.CreatePaymentRequest;
import com.store.management.system.store_management.dto.payment.response.PaymentResponse;
import com.store.management.system.store_management.entity.Employee;
import com.store.management.system.store_management.entity.Invoice;
import com.store.management.system.store_management.entity.Payment;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.EmployeeRepo;
import com.store.management.system.store_management.repo.InvoiceRepo;
import com.store.management.system.store_management.repo.PaymentRepo;
import com.store.management.system.store_management.specification.PaymentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PaymentService {


    private final PaymentRepo  paymentRepo;
    private final InvoiceRepo invoiceRepo;
    private final EmployeeRepo employeeRepo;

    public PaymentService(PaymentRepo paymentRepo, InvoiceRepo invoiceRepo,
                          EmployeeRepo employeeRepo) {
        this.paymentRepo = paymentRepo;
        this.invoiceRepo = invoiceRepo;
        this.employeeRepo = employeeRepo;
    }
    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setInvoiceId(payment.getInvoice().getId());
        response.setEmployeeId(payment.getEmployee().getId());
        response.setAmount(payment.getAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setReferenceNumber(payment.getReferenceNumber());
        response.setNotes(payment.getNotes());
        response.setCreatedDate(payment.getCreatedDate());
        response.setUpdatedDate(payment.getUpdatedDate());
        return response;
    }
    private Authentication getAuth() {
        return  SecurityContextHolder.getContext().getAuthentication();
    }
    private Employee getCurrentEmployee() {
        String username = getAuth().getName();
        return employeeRepo.findByUserUsername(username)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee profile not found"));
    }

    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        Payment payment = new Payment();

        Employee employee = getCurrentEmployee();
        payment.setEmployee(employee);

        Invoice invoice = invoiceRepo.findById(request.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));


        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaidAmountMustBePositiveException("Paid amount must be positive");
        }
        if(request.getAmount().compareTo(invoice.getRemainingAmount()) > 0) {
            throw new OverPaymentException ("Payment amount cannot exceed remaining amount");
        }
        if (invoice.getInvoiceStatus() != Invoice.InvoiceStatus.POSTED){
            throw new InvoiceNotPostedException("Only posted invoices can receive payments");
        }

        payment.setInvoice(invoice);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(request.getPaymentDate());

        Payment.PaymentMethod paymentMethod =
                request.getPaymentMethod() != null
                        ? request.getPaymentMethod()
                        : Payment.PaymentMethod.CASH;

        payment.setPaymentMethod(paymentMethod);

        if (paymentMethod == Payment.PaymentMethod.CREDIT_CARD &&
                (request.getReferenceNumber() == null || request.getReferenceNumber().isBlank())) {
            throw new PaymentReferenceIsRequiredException("Payment Reference is required with Credit Card Payment");
        }

        if (request.getReferenceNumber() != null &&
                !request.getReferenceNumber().isBlank() &&
                paymentRepo.existsByReferenceNumber(request.getReferenceNumber())) {
            throw new PaymentReferenceAlreadyExistException("Payment reference already exists");
        }
        payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setNotes(request.getNotes());

        BigDecimal paidAmount = invoice.getPaidAmount().add(request.getAmount());
        BigDecimal remainingAmount = invoice.getFinalAmount().subtract(paidAmount);
        invoice.setPaidAmount(paidAmount);
        invoice.setRemainingAmount(remainingAmount);

        if  (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setPaymentStatus(Invoice.PaymentStatus.PAID);
        }else{
            invoice.setPaymentStatus(Invoice.PaymentStatus.PARTIALLY_PAID);
        }

        Payment savedPayment = paymentRepo.save(payment);
        return mapToResponse(savedPayment);
    }

    public Page<PaymentResponse> getAll(
            Integer invoiceId,
            Integer employeeId,
            Payment.PaymentMethod paymentMethod,
            Payment.PaymentStatus paymentStatus,
            String referenceNumber,
            LocalDate paymentDate,
            Pageable pageable){
        Specification<Payment> spec = PaymentSpecification.filter(invoiceId,employeeId,paymentMethod,paymentStatus,
                referenceNumber,paymentDate);
        return paymentRepo.findAll(spec,pageable)
                .map(this::mapToResponse);
    }

    public PaymentResponse getPaymentById(Integer id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow (()->new PaymentNotFoundException("Payment not found with id : "+ id));

        return mapToResponse(payment);

    }
}
