package com.ledgerlink.posting.service;

import com.ledgerlink.posting.domain.Account;
import com.ledgerlink.posting.domain.Direction;
import com.ledgerlink.posting.domain.LedgerEntry;
import com.ledgerlink.posting.domain.Posting;
import com.ledgerlink.posting.repository.AccountRepository;
import com.ledgerlink.posting.repository.LedgerEntryRepository;
import com.ledgerlink.posting.repository.PostingRepository;
import com.ledgerlink.posting.support.Money;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostingService {

    private final PostingRepository postingRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public PostingService(PostingRepository postingRepository,
                          LedgerEntryRepository ledgerEntryRepository,
                          AccountRepository accountRepository) {
        this.postingRepository = postingRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Posting post(String clientReference, String debitAccountId, String creditAccountId,
                        long amountMinor, LocalDate valueDate, String narrative) {

        if (!Money.isPositive(amountMinor)) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        if (!Money.isWithinPostingLimit(amountMinor)) {
            throw new InvalidAmountException(
                    "Amount exceeds the single-posting limit of "
                            + Money.format(Money.MAX_POSTING_MINOR, "minor units"));
        }
        if (debitAccountId.equals(creditAccountId)) {
            throw new InvalidAmountException("Debit and credit accounts must differ");
        }

        Account debit = accountRepository.findById(debitAccountId)
                .orElseThrow(() -> new UnknownAccountException(debitAccountId));
        Account credit = accountRepository.findById(creditAccountId)
                .orElseThrow(() -> new UnknownAccountException(creditAccountId));

        if (!debit.getCurrency().equals(credit.getCurrency())) {
            throw new CurrencyMismatchException(debit.getCurrency(), credit.getCurrency());
        }

        String currency = debit.getCurrency();
        LocalDate effectiveValueDate = valueDate != null ? valueDate : LocalDate.now();

        Posting posting = new Posting(UUID.randomUUID().toString(), clientReference,
                debitAccountId, creditAccountId, amountMinor, currency,
                effectiveValueDate, narrative);
        postingRepository.save(posting);

        ledgerEntryRepository.save(new LedgerEntry(posting.getId(), debitAccountId,
                Direction.DEBIT, amountMinor, currency, effectiveValueDate));
        ledgerEntryRepository.save(new LedgerEntry(posting.getId(), creditAccountId,
                Direction.CREDIT, amountMinor, currency, effectiveValueDate));

        return posting;
    }

    @Transactional(readOnly = true)
    public Posting findById(String postingId) {
        return postingRepository.findById(postingId)
                .orElseThrow(() -> new PostingNotFoundException(postingId));
    }
}
