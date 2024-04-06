package com.callbackCrew.FrauddetectionBackend.service.impl;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;
import com.callbackCrew.FrauddetectionBackend.entity.PaymentResponse;
import com.callbackCrew.FrauddetectionBackend.repository.PaymentRepository;
import com.callbackCrew.FrauddetectionBackend.service.PaymentService;
import com.callbackCrew.FrauddetectionBackend.service.WebClient.MLCallClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO generateFraudResponse(Payment paymentInfo) {
        Payment payment = paymentRepository.save(paymentInfo);
//        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
//        List<String> rulesViolated = new ArrayList<>();
//        rulesViolated.add("RULE-001");
//        rulesViolated.add("RULE-002");
//        paymentResponseDTO.setStatus("ALERT");
//        paymentResponseDTO.setRuleViolated(rulesViolated);
//        Instant instant = Instant.now();
//        // Create a Timestamp object from the Instant2w
//        Timestamp timestamp = Timestamp.from(instant);
//        // Convert Timestamp to string
//        String timestampString = timestamp.toString();
//        paymentResponseDTO.setTimestamp(timestampString);
//        return paymentResponseDTO;
        PaymentResponseDTO mLCall = MLCallClient.webClientCall(paymentInfo);
        if(validateRule1(paymentInfo)){
            mLCall.getRuleViolated().add("RULE-001");
        }
        if(validateRule2(paymentInfo)){
            mLCall.getRuleViolated().add("RULE-002");
        }
        return null;
    }

}
