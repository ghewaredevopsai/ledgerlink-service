package com.ledgerlink.posting.api;

import com.ledgerlink.posting.domain.Posting;
import com.ledgerlink.posting.service.BalanceService;
import com.ledgerlink.posting.service.PostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostingController {

    private final PostingService postingService;
    private final BalanceService balanceService;

    public PostingController(PostingService postingService, BalanceService balanceService) {
        this.postingService = postingService;
        this.balanceService = balanceService;
    }

    @PostMapping("/postings")
    public ResponseEntity<PostingResponse> create(@Valid @RequestBody PostingRequest request) {
        Posting posting = postingService.post(
                request.getClientReference(),
                request.getDebitAccountId(),
                request.getCreditAccountId(),
                request.getAmountMinor(),
                request.getValueDate(),
                request.getNarrative());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostingResponse(posting));
    }

    @GetMapping("/postings/{postingId}")
    public PostingResponse get(@PathVariable String postingId) {
        return new PostingResponse(postingService.findById(postingId));
    }

    @GetMapping("/accounts/{accountId}/balance")
    public BalanceResponse balance(@PathVariable String accountId) {
        return new BalanceResponse(accountId,
                balanceService.balanceMinorFor(accountId),
                balanceService.currencyFor(accountId));
    }
}
