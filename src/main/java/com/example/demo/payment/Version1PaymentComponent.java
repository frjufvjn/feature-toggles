package com.example.demo.payment;

import com.example.demo.feature.FeatureOption;
import org.springframework.stereotype.Component;

@FeatureOption(defaultFeature = true)
@Component
public class Version1PaymentComponent implements PaymentService {
    @Override
    public String getPaymentStatus() {
        return "version1";
    }
}
