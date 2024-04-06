package com.callbackCrew.FrauddetectionBackend.service.impl;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;
import com.callbackCrew.FrauddetectionBackend.repository.PaymentRepository;
import com.callbackCrew.FrauddetectionBackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO generateFraudResponse(Payment paymentInfo) {
        Payment payment = paymentRepository.save(paymentInfo);
        Timestamp modeltimestamp = Timestamp.from(Instant.now());
        List<String> temp = new ArrayList<>();
        if(paymentInfo.getRrn().equals("123567")){
            temp.add("RULE-003");
        }
        if(paymentInfo.getCardAcceptorTerminalId().equals("987421")){
            temp.add("RULE-004");
        }
        PaymentResponseDTO modelResponse = new PaymentResponseDTO("OK",temp, modeltimestamp.toString());

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        List<String> violatedRulesList = getViolatedRules(paymentInfo);
        violatedRulesList.addAll(modelResponse.getRuleViolated());
        if(violatedRulesList.size()>1)
        {
            paymentResponseDTO.setStatus("ALERT");
        }
        else
        {
            paymentResponseDTO.setStatus("OK");
        }
        paymentResponseDTO.setRuleViolated(violatedRulesList);
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        String timestampString = timestamp.toString();
        paymentResponseDTO.setTimestamp(timestampString);
        return paymentResponseDTO;
    }

    @Override
    public List<String> getViolatedRules(Payment paymentInfo) {
        long twelveHoursAgoEpoch = Instant.now().minusSeconds(12 * 60 * 60).getEpochSecond();
        List<String> violatedRulesList = new ArrayList<>();
        List<Payment> paymentList = paymentRepository.findByEncryptedPanAndDateTimeTransactionLessThanEqualOrderByDateTimeTransactionAsc(paymentInfo.getEncryptedPan(), twelveHoursAgoEpoch);
        if(paymentList.size()>0){
            double initialBalance = Double.parseDouble(paymentList.get(0).getCardBalance()) + Double.parseDouble(paymentList.get(0).getTransactionAmount());
            double totalTransactionAmount = 0;
            for(Payment payment: paymentList)
            {
                totalTransactionAmount += Double.parseDouble(payment.getTransactionAmount());
            }
            if(totalTransactionAmount >= 0.7*initialBalance && initialBalance >= 300000)
            {
                violatedRulesList.add("RULE-001");
            }
            int greaterDistCount=0;
            for(Payment payment1: paymentList)
            {
                for(Payment payment2: paymentList)
                {
                    if(payment1.getLatitude().equals(payment2.getLatitude()) && payment1.getLongitude().equals(payment2.getLongitude()))
                    {
                        continue;
                    }
                    double dist = distance(Double.parseDouble(payment1.getLatitude()),Double.parseDouble(payment2.getLatitude()),Double.parseDouble(payment1.getLongitude()),Double.parseDouble(payment2.getLongitude()));
                    if(dist>200)
                    {
                        greaterDistCount += 1;
                        if(greaterDistCount>5)
                        {
                            violatedRulesList.add("RULE-002");
                            return violatedRulesList;
                        }
                    }
                }
            }
        }
        return violatedRulesList;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
