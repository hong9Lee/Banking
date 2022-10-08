package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.WithdrawRequest;
import banking.dto.response.WithdrawResponse;
import banking.helper.enums.StatusCode;
import banking.helper.exception.BalanceException;
import banking.helper.exception.UnKnownAccountException;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawService {

    /**
     * 출금 관련 서비스 로직
     */


    private final AccountRepository accountRepository;

    public WithdrawResponse withdraw(WithdrawRequest req) {
        try {
            Account account = isExistToken(req.getPrivateToken());
            if (account == null) throw new RuntimeException();
            return withdrawMoney(req, account);
        } catch (Exception e) {
            System.out.println("WITH ERROR !!!!");
            throw new UnKnownAccountException(StatusCode.UNKNOWN_WITHDRAW_ACCOUNT);
        }
    }

    /**
     * privateToken 으로 계좌를 찾는 메서드
     */
    public Account isExistToken(String privateToken) {
        return accountRepository.findByPrivateToken(privateToken);
    }

    @Transactional
    public WithdrawResponse withdrawMoney(WithdrawRequest req, Account account) {
        long money = account.getMoney();

        if (money - req.getMoney() < 0)
            throw new BalanceException(StatusCode.LACK_OF_BALANCE.getMsg(), account.getUserId(), money);

        account.setMoney(money - req.getMoney());
        account.setCalcMoney(-req.getMoney());

        Account savedAccount = accountRepository.save(account);
        return createResponse(savedAccount, StatusCode.SUCCESS);
    }

    public WithdrawResponse createResponse(Account account, StatusCode status) {
        return WithdrawResponse.builder()
                .userId(account.getUserId())
                .code(status.getCode())
                .balance(account.getMoney())
                .build();
    }


}
