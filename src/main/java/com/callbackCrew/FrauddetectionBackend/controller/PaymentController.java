package com.callbackCrew.FrauddetectionBackend.controller;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;
import com.callbackCrew.FrauddetectionBackend.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/mainRequest")
    public ResponseEntity<PaymentResponseDTO> generateResponse(@RequestBody Payment paymentReq){
        PaymentResponseDTO paymentResponse = paymentService.generateFraudResponse(paymentReq);
        ResponseEntity<PaymentResponseDTO> responseEntity = new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
        return responseEntity;
    }
}
