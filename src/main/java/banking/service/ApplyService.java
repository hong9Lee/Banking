package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.ApplyRequest;
import banking.dto.response.ApplyResponse;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyService {

    /**
     * 계좌 생성 관련 서비스 로직
     */


    private final AccountRepository accountRepository;

    /** 계좌 생성 메서드 */
    public ApplyResponse createAccount(ApplyRequest applyRequest) {
        if (!isExistUser(applyRequest.getUserId())) return createResponse(createUser(applyRequest));
        else {
            throw new DuplicateKeyException("중복된 ID가 존재합니다.");
        }
    }

    /**
     * 동일 ID 체크
     */
    public boolean isExistUser(String userId) {
        return accountRepository.existsByUserId(userId);
    }

    /**
     * User(사용자 ID, 공개키, 비밀키) 생성 및 반환
     */
    @Transactional
    public Account createUser(ApplyRequest applyRequest) {
        String userId = applyRequest.getUserId();

        Account newUser = new Account();
        newUser.setUserId(userId);
        newUser.setPublicToken(UUID.randomUUID().toString());
        newUser.setPrivateToken(UUID.randomUUID().toString());
        return accountRepository.save(newUser);
    }

    /** Response 생성 메서드 */
    public ApplyResponse createResponse(Account account) {
        return ApplyResponse.builder()
                .userId(account.getUserId())
                .publicToken(account.getPublicToken())
                .privateToken(account.getPrivateToken())
                .build();
    }
}
