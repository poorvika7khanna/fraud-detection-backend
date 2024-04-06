package com.callbackCrew.FrauddetectionBackend.repository;

import com.callbackCrew.FrauddetectionBackend.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment,Integer>
{

}
