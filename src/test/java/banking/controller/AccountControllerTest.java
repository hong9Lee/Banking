package banking.controller;

import banking.dto.AccountUser;
import banking.dto.User;
import banking.repository.AccountUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    AccountUserRepository accountUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Description("계좌 신청 성공 테스트")
    public void 계좌신청_성공_테스트() throws Exception {
        AccountUser user = new AccountUser();
        user.setUserId("TestUser1");

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @Description("유저 아이디 중복 테스트")
    public void 유저아이디_중복_테스트() throws Exception {
        AccountUser user = new AccountUser();
        user.setUserId("TestUser1");
        accountUserRepository.save(user);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Description("유저 아이디 유효성 테스트")
    public void 유저아이디_유효성_테스트() throws Exception {
        AccountUser user = new AccountUser();
        user.setUserId("홍규");

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("필수 파라미터 유효성 테스트")
    public void 필수파라미터_테스트() throws Exception {
        User user = new User();

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
