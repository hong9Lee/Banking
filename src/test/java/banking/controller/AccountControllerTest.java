package banking.controller;

import banking.dto.entity.Account;
import banking.dto.request.DepositRequest;
import banking.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Description("입금 성공 테스트")
    void 입금_성공_테스트() throws Exception {
        Account account = new Account();
        account.setUserId("TestDepositUser1");
        account.setPublicToken(UUID.randomUUID().toString());
        account.setPrivateToken(UUID.randomUUID().toString());
        accountRepository.save(account);

        DepositRequest accountRequest = new DepositRequest();
        accountRequest.setPublicToken(account.getPublicToken());
        accountRequest.setMoney(1000);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(mapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Account getAccount = accountRepository.findByPublicToken(account.getPublicToken());
        assertEquals(getAccount.getMoney(), 1000);
    }

    @Test
    @Description("money 유효성 테스트")
    void 필수파라미터_money_유효성테스트() throws Exception {
        DepositRequest accountRequest = new DepositRequest();
        accountRequest.setPublicToken(UUID.randomUUID().toString());
        accountRequest.setMoney(-1);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(mapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("필수 파라미터(money) 유효성 테스트")
    void 필수파라미터_money_테스트() throws Exception {
        DepositRequest accountRequest = new DepositRequest();
        accountRequest.setPublicToken(UUID.randomUUID().toString());

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(mapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("money 유효성 테스트")
    void 필수파라미터_publicToken_유효성테스트() throws Exception {
        DepositRequest accountRequest = new DepositRequest();
        accountRequest.setPublicToken("123456789");
        accountRequest.setMoney(1000);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(mapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("필수 파라미터(publicToken) 유효성 테스트")
    void 필수파라미터_publicToken_테스트() throws Exception {
        DepositRequest accountRequest = new DepositRequest();
        accountRequest.setMoney(1000);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(mapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }





}
