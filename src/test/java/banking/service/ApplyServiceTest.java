package banking.service;

import banking.dto.AccountUser;
import banking.dto.User;
import banking.repository.AccountUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    AccountUserRepository accountUserRepository;

    @Autowired
    ApplyService applyService;

    AccountUser givenUser() {
        AccountUser accountUser = new AccountUser();
        accountUser.setUserId("TestUser1");
        return accountUserRepository.save(accountUser);
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
        AccountUser user = new AccountUser();
        user.setUserId("TestUser2");
        boolean isExist = applyService.isExistUser(user.getUserId());
        assertFalse(isExist);
    }

    @Test
    @Description("유저 생성 성공 테스트")
    public void saveUser_성공_테스트() {
        User user = new User("saveTestUser1");
        AccountUser savedUser = applyService.createUser(user);
        assertEquals(user.getUserId(), savedUser.getUserId());
    }


}
