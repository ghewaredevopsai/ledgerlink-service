package com.ledgerlink.posting.api;

public class BalanceResponse {

    private final String accountId;
    private final long balanceMinor;
    private final String currency;

    public BalanceResponse(String accountId, long balanceMinor, String currency) {
        this.accountId = accountId;
        this.balanceMinor = balanceMinor;
        this.currency = currency;
    }

    public String getAccountId() {
        return accountId;
    }

    public long getBalanceMinor() {
        return balanceMinor;
    }

    public String getCurrency() {
        return currency;
    }
}
