package com.ledgerlink.posting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ledgerlink.posting.domain.Posting;
import com.ledgerlink.posting.service.BalanceService;
import com.ledgerlink.posting.service.CurrencyMismatchException;
import com.ledgerlink.posting.service.InvalidAmountException;
import com.ledgerlink.posting.service.PostingService;
import com.ledgerlink.posting.service.UnknownAccountException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({PostingService.class, BalanceService.class})
class PostingServiceTest {

    @Autowired
    private PostingService postingService;

    @Autowired
    private BalanceService balanceService;

    @Test
    void postsBothSidesOfTheEntry() {
        Posting posting = postingService.post("PAY-0001", "ACC-CLIENT-001", "ACC-FEES",
                12_34L, LocalDate.of(2026, 3, 1), "Advisory fee");

        assertThat(posting.getId()).isNotBlank();
        assertThat(posting.getCurrency()).isEqualTo("GBP");
        assertThat(balanceService.balanceMinorFor("ACC-CLIENT-001")).isEqualTo(-12_34L);
        assertThat(balanceService.balanceMinorFor("ACC-FEES")).isEqualTo(12_34L);
    }

    @Test
    void rejectsANonPositiveAmount() {
        assertThatThrownBy(() -> postingService.post("PAY-0002", "ACC-CLIENT-001", "ACC-FEES",
                0L, LocalDate.of(2026, 3, 1), "Nothing"))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void rejectsAnUnknownAccount() {
        assertThatThrownBy(() -> postingService.post("PAY-0003", "ACC-NOPE", "ACC-FEES",
                100L, LocalDate.of(2026, 3, 1), "Missing"))
                .isInstanceOf(UnknownAccountException.class);
    }

    @Test
    void rejectsACrossCurrencyPosting() {
        assertThatThrownBy(() -> postingService.post("PAY-0004", "ACC-CLIENT-001", "ACC-USD-001",
                100L, LocalDate.of(2026, 3, 1), "Cross currency"))
                .isInstanceOf(CurrencyMismatchException.class);
    }
}
