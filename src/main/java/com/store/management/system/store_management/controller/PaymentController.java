package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.payment.request.CreatePaymentRequest;
import com.store.management.system.store_management.dto.payment.response.PaymentResponse;
import com.store.management.system.store_management.entity.Payment;
import com.store.management.system.store_management.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentResponse createPayment( @Valid @RequestBody CreatePaymentRequest createPaymentRequest){
        return paymentService.createPayment(createPaymentRequest);
    }
    @GetMapping
    public Page<PaymentResponse> getAll (
            @RequestParam(required=false) Integer invoiceId,
            @RequestParam(required=false) Integer employeeId,
            @RequestParam(required=false) Payment.PaymentMethod paymentMethod,
            @RequestParam(required=false) Payment.PaymentStatus paymentStatus,
            @RequestParam(required=false) String referenceNumber,
            @RequestParam(required=false) LocalDate paymentDate,
            Pageable pageable){

        return paymentService.getAll(invoiceId,employeeId,paymentMethod,paymentStatus,
                referenceNumber,paymentDate,pageable);
    }
    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Integer id){
        return  paymentService.getPaymentById(id);
    }

}
