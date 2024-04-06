package com.callbackCrew.FrauddetectionBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
    private String status;
    private List<String> ruleViolated;
    private String timestamp;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String status, List<String> ruleViolated, String timestamp) {
        this.status = status;
        this.ruleViolated = ruleViolated;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRuleViolated() {
        return ruleViolated;
    }

    public void setRuleViolated(List<String> ruleViolated) {
        this.ruleViolated = ruleViolated;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
