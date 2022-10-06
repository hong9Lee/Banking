package banking.controller;

import banking.dto.entity.Account;
import banking.dto.request.ApplyRequest;
import banking.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApplyControllerTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Description("계좌 신청 성공 테스트")
    public void 계좌신청_성공_테스트() throws Exception {
        Account user = new Account();
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
        Account user = new Account();
        user.setUserId("TestUser1");
        accountRepository.save(user);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.registerModule(new JavaTimeModule())
                                .writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Description("파라미터(유저 아이디) 유효성 테스트")
    public void 유저아이디_유효성_테스트() throws Exception {
        Account user = new Account();
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
    public void 필수파라미터_유저아이디_테스트() throws Exception {
        ApplyRequest applyRequest = new ApplyRequest();

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/apply")
                        .content(mapper.writeValueAsString(applyRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
