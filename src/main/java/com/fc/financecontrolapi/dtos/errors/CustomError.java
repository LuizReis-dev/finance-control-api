package com.fc.financecontrolapi.dtos.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
