package banking.service;

import banking.dto.entity.Account;
import banking.dto.request.ApplyRequest;
import banking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApplyServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ApplyService applyService;

    Account givenUser() {
        Account account = new Account();
        account.setUserId("TestUser1");
        return accountRepository.save(account);
    }

    @Test
    @Description("임의로 생성한 유저 아이디 중복 성공 체크")
    public void isExistUser_성공_테스트() {
        boolean isExist = applyService.isExistUser(givenUser().getUserId());
        assertTrue(isExist);
    }

    @Test
    @Description("임의로 생성한 유저 아이디 중복 실패 체크")
    public void isExistUser_실패_테스트() {
        Account user = new Account();
        user.setUserId("TestUser2");
        boolean isExist = applyService.isExistUser(user.getUserId());
        assertFalse(isExist);
    }

    @Test
    @Description("유저 생성 성공 테스트")
    public void saveUser_성공_테스트() {
        ApplyRequest applyRequest = new ApplyRequest("saveTestUser1");
        Account savedUser = applyService.createUser(applyRequest);
        assertEquals(applyRequest.getUserId(), savedUser.getUserId());
    }


}
