package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.DepositRequest;
import banking.dto.response.DepositResponse;
import banking.helper.enums.StatusCode;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    /**
     * 입출금 관련 서비스 로직
     */

    private final AccountRepository accountRepository;

    /** 입금 처리 메서드 */
    public DepositResponse deposit(DepositRequest deposit) {
        Account account = isExistToken(deposit.getPublicToken());
        if(account != null) return saveMoney(deposit, account);
        else {
            return createResponse(deposit, StatusCode.FAIL);
        }
    }

    /** publicToken 으로 계좌를 찾는 메서드 */
    private Account isExistToken(String publicToken) {
        return accountRepository.findByPublicToken(publicToken);
    }

    /** 입금 처리 db에 반영하는 메서드 */
    private DepositResponse saveMoney(DepositRequest deposit, Account account) {
        long money = account.getMoney();
        account.setMoney(deposit.getMoney() + money);
        account.setCalcMoney(deposit.getMoney());

        accountRepository.save(account);
        return createResponse(deposit, StatusCode.SUCCESS);
    }


    /** Response 생성 메서드 */
    private DepositResponse createResponse(DepositRequest deposit, StatusCode status) {
        DepositResponse depositResponse = new DepositResponse();
        depositResponse.setPublicToken(deposit.getPublicToken());
        depositResponse.setCode(status.getCode());
        return depositResponse;
    }





}
