package com.fc.financecontrolapi.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 3)
    private String password;
}
