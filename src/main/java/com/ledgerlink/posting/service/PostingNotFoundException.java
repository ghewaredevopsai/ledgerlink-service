package com.ledgerlink.posting.service;

import org.springframework.http.HttpStatus;

public class PostingNotFoundException extends LedgerException {

    public PostingNotFoundException(String postingId) {
        super(HttpStatus.NOT_FOUND, "posting-not-found",
                "No posting with id " + postingId);
    }
}
