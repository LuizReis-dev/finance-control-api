package com.fc.financecontrolapi.dtos.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequestDTO {
    @JsonProperty("category_id")
    private Long categoryId;
    private Integer amount;
    @JsonProperty("payment_method")
    @NotBlank
    private String paymentMethod;
    private String description;
    private Integer installments;
    @JsonProperty("initial_month")
    private Integer initialMonth;
}
