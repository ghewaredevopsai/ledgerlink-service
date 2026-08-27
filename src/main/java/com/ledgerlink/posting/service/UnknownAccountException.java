package com.ledgerlink.posting.service;

import org.springframework.http.HttpStatus;

public class UnknownAccountException extends LedgerException {

    public UnknownAccountException(String accountId) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "unknown-account",
                "No account with id " + accountId);
    }
}
