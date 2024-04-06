package com.callbackCrew.FrauddetectionBackend.service;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;

public interface PaymentService {

    public PaymentResponseDTO generateFraudResponse(Payment paymentReq);
}
