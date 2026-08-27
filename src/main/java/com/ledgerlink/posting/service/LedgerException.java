package com.ledgerlink.posting.service;

import org.springframework.http.HttpStatus;

/**
 * Base type for every failure this service reports to a caller.
 * Carries the status so the advice does not have to map exception types.
 */
public class LedgerException extends RuntimeException {

    private final HttpStatus status;
    private final String reason;

    public LedgerException(HttpStatus status, String reason, String message) {
        super(message);
        this.status = status;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
