package banking.service;

import banking.dto.AccountUser;
import banking.dto.User;
import banking.repository.AccountUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final AccountUserRepository accountUserRepository;

    public AccountUser createAccount(User user) {
        boolean existUser = isExistUser(user.getUserId());
        if (!existUser) return createUser(user);
        else {
            throw new DuplicateKeyException("중복된 ID가 존재합니다.");
        }
    }

    /**
     * 동일 ID 체크
     */
    public boolean isExistUser(String userId) {
        return accountUserRepository.existsByUserId(userId);
    }

    /**
     * User(사용자 ID, 공개키, 비밀키) 생성 및 반환
     */
    @Transactional
    public AccountUser createUser(User user) {
        String userId = user.getUserId();

        AccountUser newUser = new AccountUser();
        newUser.setUserId(userId);
        newUser.setPublicToken(UUID.randomUUID().toString());
        newUser.setPrivateToken(UUID.randomUUID().toString());
        return accountUserRepository.save(newUser);
    }
}
