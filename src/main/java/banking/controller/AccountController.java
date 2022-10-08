package banking.controller;

import banking.dto.request.DepositRequest;
import banking.dto.request.TransferRequest;
import banking.dto.request.TransferResultRequest;
import banking.dto.request.WithdrawRequest;
import banking.dto.response.DepositResponse;
import banking.dto.response.TransferResponse;
import banking.dto.response.TransferResultResponse;
import banking.dto.response.WithdrawResponse;
import banking.service.DepositService;
import banking.service.TransferService;
import banking.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final TransferService transferService;

    /**
     * 입금
     */
    @PostMapping("/deposit")
    public ResponseEntity deposit(final @Valid @RequestBody DepositRequest req) {
        DepositResponse result = depositService.deposit(req);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 출금
     */
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(final @Valid @RequestBody WithdrawRequest req) {
        WithdrawResponse result = withdrawService.withdraw(req);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 계좌 이체
     */
    @PostMapping("/transfer")
    public ResponseEntity transfer(final @Valid @RequestBody TransferRequest req) {
        TransferResponse result = transferService.transfer(req);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 거래 결과
     */
    @PostMapping("/result")
    public ResponseEntity transferResult(final @Valid @RequestBody TransferResultRequest req) {
        TransferResultResponse result = transferService.getTransferResult(req);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
