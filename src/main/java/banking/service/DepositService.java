package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.DepositRequest;
import banking.dto.response.DepositResponse;
import banking.helper.enums.StatusCode;
import banking.helper.exception.UnKnownAccountException;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class DepositService {

    /**
     * 입금 관련 서비스 로직
     */

    private final AccountRepository accountRepository;

    /**
     * 입금 처리 메서드
     */
    public DepositResponse deposit(DepositRequest req) {
        try {
            Account account = isExistToken(req.getPublicToken());
            if (account == null) throw new RuntimeException();
            return saveMoney(req, account);
        } catch (Exception e) {
            System.out.println("DEPOSIT ERROR !!!!");
            throw new UnKnownAccountException(StatusCode.UNKNOWN_DEPOSIT_ACCOUNT);
        }
    }

    /**
     * publicToken 으로 계좌를 찾는 메서드
     */
    public Account isExistToken(String publicToken) {
        return accountRepository.findByPublicToken(publicToken);
    }

    /**
     * 입금 처리 db에 반영하는 메서드
     */
    @Transactional
    public DepositResponse saveMoney(DepositRequest req, Account account) {
        long money = account.getMoney();
        account.setMoney(req.getMoney() + money);
        account.setCalcMoney(req.getMoney());

        accountRepository.save(account);
        return createResponse(account, StatusCode.SUCCESS);
    }


    /**
     * Response 생성 메서드
     */
    public DepositResponse createResponse(Account account, StatusCode status) {
        return DepositResponse.builder()
                .userId(account.getUserId())
                .code(status.getCode())
                .build();
    }


}
