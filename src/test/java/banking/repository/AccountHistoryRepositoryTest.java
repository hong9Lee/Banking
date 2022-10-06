package banking.repository;

import banking.dto.entity.Account;
import banking.dto.entity.AccountHistory;
import banking.dto.request.DepositRequest;
import banking.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountHistoryRepositoryTest {

    @Autowired
    AccountHistoryRepository historyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void deleteHistory() {
        historyRepository.deleteAll();
    }

    @Test
    @Description("History 저장 Test")
    void history_테스트() {
        Account account = new Account();
        account.setPublicToken(UUID.randomUUID().toString());
        account.setPrivateToken(UUID.randomUUID().toString());
        accountRepository.save(account);

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setPublicToken(account.getPublicToken());
        depositRequest.setMoney(10000);
        accountService.deposit(depositRequest);


        List<AccountHistory> findHistory = historyRepository.findAll();
        AccountHistory accountHistory = findHistory.get(0);
        assertEquals(accountHistory.getAccount().getId(), account.getId());
    }

    @Test
    @Description("Account 필드 HistoryList calcMoney Test")
    void Account_historyList_테스트() {
        Account account = new Account();
        account.setPublicToken(UUID.randomUUID().toString());
        account.setPrivateToken(UUID.randomUUID().toString());
        account.setMoney(100);
        accountRepository.save(account);

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setPublicToken(account.getPublicToken());
        depositRequest.setMoney(1000);
        accountService.deposit(depositRequest);

        Account findAccount = accountRepository.findByPublicToken(account.getPublicToken());

        DepositRequest depositRequest1 = new DepositRequest();
        depositRequest1.setPublicToken(findAccount.getPublicToken());
        depositRequest1.setMoney(2000);
        accountService.deposit(depositRequest1);

        List<AccountHistory> findHistory = historyRepository.findAll();
        assertEquals(findHistory.get(0).getCalcMoney(), 1000);
        assertEquals(findHistory.get(1).getCalcMoney(), 2000);
    }


}
