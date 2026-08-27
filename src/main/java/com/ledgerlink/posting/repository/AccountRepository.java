package com.ledgerlink.posting.repository;

import com.ledgerlink.posting.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
