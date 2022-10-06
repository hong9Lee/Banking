package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.DepositRequest;
import banking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Test
    @Description("deposit 메소드 테스트")
    void deposit_성공_테스트() {
        Account account = new Account();
        account.setPublicToken(UUID.randomUUID().toString());
        account.setMoney(1000);
        accountRepository.save(account);

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setPublicToken(account.getPublicToken());
        depositRequest.setMoney(1000);

        accountService.deposit(depositRequest);

        Account findAccount = accountRepository.findByPublicToken(account.getPublicToken());
        assertEquals(findAccount.getMoney(), 2000);
    }

}
