package com.ledgerlink.posting.service;

import org.springframework.http.HttpStatus;

public class InvalidAmountException extends LedgerException {

    public InvalidAmountException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "invalid-amount", message);
    }
}
