package com.ledgerlink.posting.service;

import org.springframework.http.HttpStatus;

public class CurrencyMismatchException extends LedgerException {

    public CurrencyMismatchException(String debitCurrency, String creditCurrency) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "currency-mismatch",
                "Cannot post between accounts in " + debitCurrency + " and " + creditCurrency);
    }
}
