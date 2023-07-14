package com.example.demo.payment;

import org.springframework.stereotype.Component;

@Component
public class Version2PaymentComponent implements PaymentService {
    @Override
    public String getPaymentStatus() {
        return "version2";
    }
}
