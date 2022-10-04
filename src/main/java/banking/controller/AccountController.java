package banking.controller;

import banking.dto.AccountUser;
import banking.dto.User;
import banking.service.ApplyService;
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

    private final ApplyService applyService;

    /**
     * 계좌 신청
     */
    @PostMapping("/apply")
    public ResponseEntity openAccount(final @Valid @RequestBody User user) {
        AccountUser accountUser = applyService.createAccount(user);
        return new ResponseEntity<>(accountUser, HttpStatus.OK);
    }

}
