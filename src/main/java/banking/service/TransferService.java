package banking.service;

import banking.dto.entity.TransferHistory;
import banking.dto.request.DepositRequest;
import banking.dto.request.TransferRequest;
import banking.dto.request.TransferResultRequest;
import banking.dto.request.WithdrawRequest;
import banking.dto.response.TransferResponse;
import banking.dto.response.TransferResultResponse;
import banking.helper.enums.StatusCode;
import banking.helper.exception.TransferException;
import banking.repository.TransferHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransferService {

    public final TransferHistoryRepository historyRepository;

    public TransferResponse transfer(TransferRequest req) {
        try {
            withdraw(req);
            deposit(req);
            String key = createHistory(req, StatusCode.SUCCESS);
            return createResponse(key);
        } catch (Exception e) {
            createHistory(req, StatusCode.TRANSFER_ERROR);
            log.error("transfer =======>" + e.getMessage());
            throw new TransferException(StatusCode.TRANSFER_ERROR);
        }
    }

    public TransferResponse createResponse(String key) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTransferKey(key);
        return transferResponse;
    }

    public String createHistory(TransferRequest req, StatusCode status) {
        // 출금 하는 계좌
//        Account withdrawer = accountRepository.findByPrivateToken(req.getPrivateToken());
        // 입금 받는 계좌
//        Account depositor = accountRepository.findByPublicToken(req.getPublicToken());

        String transferKey = UUID.randomUUID().toString();
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setTransferKey(transferKey);
//        transferHistory.setDepositAccount(depositor);
//        transferHistory.setWithdrawAccount(withdrawer);

        transferHistory.setWithdrawAccount(req.getPrivateToken());
        transferHistory.setDepositAccount(req.getPublicToken());

        transferHistory.setMoney(req.getMoney());
        transferHistory.setStatus(status.getCode());
        transferHistory.setMsg(status.getMsg());

        historyRepository.save(transferHistory);
        return transferKey;
    }

    public void withdraw(TransferRequest req) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8070/withdraw";

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setPrivateToken(req.getPrivateToken());
        withdrawRequest.setMoney(req.getMoney());

        restTemplate.postForEntity(url, withdrawRequest, null);
    }

    public void deposit(TransferRequest req) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8070/deposit";


        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setMoney(req.getMoney());
        depositRequest.setPublicToken(req.getPublicToken());

        restTemplate.postForEntity(url, depositRequest, null);
    }

    public TransferResultResponse getTransferResult(TransferResultRequest req) {
        TransferHistory history = historyRepository.findByTransferKey(req.getTransferKey());
        TransferResultResponse res = new TransferResultResponse();
        res.setTransferResultCode(history.getStatus());
        return res;

    }

}
