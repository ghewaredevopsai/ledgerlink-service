package com.ledgerlink.posting.support;

/**
 * Amounts are held as minor units in a long, never as a floating point type.
 * 12.34 GBP is 1234. Callers are responsible for supplying minor units.
 */
public final class Money {

    private Money() {
    }

    public static final long MAX_POSTING_MINOR = 100_000_000_00L;

    public static boolean isPositive(long amountMinor) {
        return amountMinor > 0L;
    }

    public static boolean isWithinPostingLimit(long amountMinor) {
        return amountMinor <= MAX_POSTING_MINOR;
    }

    public static String format(long amountMinor, String currency) {
        long major = amountMinor / 100L;
        long minor = Math.abs(amountMinor % 100L);
        return String.format("%d.%02d %s", major, minor, currency);
    }
}
