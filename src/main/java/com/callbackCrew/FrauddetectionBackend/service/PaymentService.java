package com.callbackCrew.FrauddetectionBackend.service;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;

import java.util.List;

public interface PaymentService {

    public PaymentResponseDTO generateFraudResponse(Payment paymentInfo);

    public List<String> getViolatedRules(Payment paymentInfo);
}
