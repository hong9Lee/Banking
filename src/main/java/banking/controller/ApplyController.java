package banking.controller;

import banking.dto.request.ApplyRequest;
import banking.dto.response.ApplyResponse;
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
public class ApplyController {

    private final ApplyService applyService;

    /**
     * 계좌 신청
     */
    @PostMapping("/apply")
    public ResponseEntity openAccount(final @Valid @RequestBody ApplyRequest applyRequest) {
        ApplyResponse account = applyService.createAccount(applyRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
