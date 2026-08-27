package com.ledgerlink.posting.repository;

import com.ledgerlink.posting.domain.LedgerEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByAccountId(String accountId);

    List<LedgerEntry> findByPostingId(String postingId);
}
