package com.ledgerlink.posting.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Posting {

    @Id
    private String id;

    /**
     * The originating system's own reference for this instruction.
     * Supplied by the caller. Not currently constrained.
     */
    @Column(nullable = false)
    private String clientReference;

    @Column(nullable = false)
    private String debitAccountId;

    @Column(nullable = false)
    private String creditAccountId;

    @Column(nullable = false)
    private long amountMinor;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private LocalDate valueDate;

    private String narrative;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostingStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    protected Posting() {
    }

    public Posting(String id, String clientReference, String debitAccountId, String creditAccountId,
                   long amountMinor, String currency, LocalDate valueDate, String narrative) {
        this.id = id;
        this.clientReference = clientReference;
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amountMinor = amountMinor;
        this.currency = currency;
        this.valueDate = valueDate;
        this.narrative = narrative;
        this.status = PostingStatus.POSTED;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getClientReference() {
        return clientReference;
    }

    public String getDebitAccountId() {
        return debitAccountId;
    }

    public String getCreditAccountId() {
        return creditAccountId;
    }

    public long getAmountMinor() {
        return amountMinor;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public String getNarrative() {
        return narrative;
    }

    public PostingStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void markReversed() {
        this.status = PostingStatus.REVERSED;
    }
}
