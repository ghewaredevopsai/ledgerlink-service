package com.ledgerlink.posting.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 * One side of a posting. Every posting produces exactly two of these,
 * one DEBIT and one CREDIT, of equal amount and currency.
 */
@Entity
public class LedgerEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String postingId;

    @Column(nullable = false)
    private String accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    @Column(nullable = false)
    private long amountMinor;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private LocalDate valueDate;

    protected LedgerEntry() {
    }

    public LedgerEntry(String postingId, String accountId, Direction direction,
                       long amountMinor, String currency, LocalDate valueDate) {
        this.postingId = postingId;
        this.accountId = accountId;
        this.direction = direction;
        this.amountMinor = amountMinor;
        this.currency = currency;
        this.valueDate = valueDate;
    }

    public Long getId() {
        return id;
    }

    public String getPostingId() {
        return postingId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Direction getDirection() {
        return direction;
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
}
