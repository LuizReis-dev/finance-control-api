package com.fc.financecontrolapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnprocessableEntityException extends RuntimeException{
    private String field;

    public UnprocessableEntityException(String field, String message) {
        super(message);
        this.field = field;
    }
}
