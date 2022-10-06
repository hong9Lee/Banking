package banking.controller;

import banking.dto.request.DepositRequest;
import banking.dto.response.DepositResponse;
import banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 입금
     */
    @PostMapping("/deposit")
    public ResponseEntity deposit(final @Valid @RequestBody DepositRequest deposit) {
        DepositResponse depositResult = accountService.deposit(deposit);
        if(depositResult.getCode() == "500") {
            return new ResponseEntity<>(depositResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(depositResult, HttpStatus.OK);
    }

    /**
     * 출금
     */
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(final @Valid @RequestBody DepositRequest deposit) {
        DepositResponse depositResult = accountService.deposit(deposit);
        return new ResponseEntity<>(depositResult, HttpStatus.OK);
    }

}
