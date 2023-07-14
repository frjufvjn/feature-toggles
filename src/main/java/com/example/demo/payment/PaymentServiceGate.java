package com.example.demo.payment;

import com.example.demo.feature.FeatureOrchestration;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class PaymentServiceGate {
    private Map<String, PaymentService> paymentServices;

    public PaymentServiceGate(Set<PaymentService> paymentServices) {
        this.paymentServices = FeatureOrchestration.compose(paymentServices);
    }
}
