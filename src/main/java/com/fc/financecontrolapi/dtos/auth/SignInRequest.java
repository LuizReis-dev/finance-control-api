package com.fc.financecontrolapi.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SignInRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
