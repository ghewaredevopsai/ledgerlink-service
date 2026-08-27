package com.ledgerlink.posting.service;

import com.ledgerlink.posting.domain.Direction;
import com.ledgerlink.posting.domain.LedgerEntry;
import com.ledgerlink.posting.repository.AccountRepository;
import com.ledgerlink.posting.repository.LedgerEntryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public BalanceService(LedgerEntryRepository ledgerEntryRepository,
                          AccountRepository accountRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Balance is derived from the entries, never stored. Credits increase, debits decrease.
     */
    @Transactional(readOnly = true)
    public long balanceMinorFor(String accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new UnknownAccountException(accountId));

        List<LedgerEntry> entries = ledgerEntryRepository.findByAccountId(accountId);
        long balance = 0L;
        for (LedgerEntry entry : entries) {
            if (entry.getDirection() == Direction.CREDIT) {
                balance += entry.getAmountMinor();
            } else {
                balance -= entry.getAmountMinor();
            }
        }
        return balance;
    }

    @Transactional(readOnly = true)
    public String currencyFor(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new UnknownAccountException(accountId))
                .getCurrency();
    }
}
