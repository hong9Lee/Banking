package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.WithdrawRequest;
import banking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WithdrawServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WithdrawService withdrawService;


    @Test
    @Description("withdraw 메소드 테스트")
    @Transactional
    void withdraw_성공_테스트() {
        Account account = new Account();
        account.setPublicToken(UUID.randomUUID().toString());
        account.setMoney(1000);
        accountRepository.save(account);

        WithdrawRequest req = new WithdrawRequest();
        req.setPrivateToken(account.getPrivateToken());
        req.setMoney(100);

        withdrawService.withdraw(req);

        Account findAccount = accountRepository.findByPrivateToken(account.getPrivateToken());
        assertEquals(findAccount.getMoney(), 900);


    }

}
