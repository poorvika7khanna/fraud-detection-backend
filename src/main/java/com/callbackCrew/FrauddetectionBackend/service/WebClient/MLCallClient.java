package com.callbackCrew.FrauddetectionBackend.service.WebClient;

import com.callbackCrew.FrauddetectionBackend.dto.PaymentResponseDTO;
import com.callbackCrew.FrauddetectionBackend.entity.Payment;
import com.callbackCrew.FrauddetectionBackend.entity.PaymentResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class MLCallClient {
    public static PaymentResponseDTO webClientCall(Payment payment) {
        WebClient webClient = WebClient.create();
        String url = "https://jsonplaceholder.typicode.com/posts";

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payment))
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .block();
    }
}


