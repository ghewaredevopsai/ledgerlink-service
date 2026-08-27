package com.ledgerlink.posting.repository;

import com.ledgerlink.posting.domain.Posting;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, String> {

    List<Posting> findByClientReference(String clientReference);

    List<Posting> findByClientReferenceAndValueDate(String clientReference, LocalDate valueDate);
}
