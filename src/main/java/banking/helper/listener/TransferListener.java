package banking.helper.listener;

import banking.dto.entity.Account;
import banking.dto.entity.AccountHistory;
import banking.helper.util.BeanUtils;
import banking.repository.AccountHistoryRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class TransferListener {

    @PreUpdate
    public void update(Object o) {
        System.out.println("UPDQTE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        Account account = (Account) o;
        AccountHistory history = buildHistory(account);


        account.addCommunityItem(history);
        AccountHistoryRepository repository = BeanUtils.getBean(AccountHistoryRepository.class);
        repository.save(history);
    }

    private AccountHistory buildHistory(Account account) {
        AccountHistory history = AccountHistory.builder()
                .account(account)
                .calcMoney(account.getCalcMoney())
                .status(getStatus(account))
                .build();
        return history;
    }

    private String getStatus(Account account) {
        String status = "";
        if(account.getCalcMoney() > 0) {
            status = "deposit";
        } else {
            status = "withdraw";
        }
        return status;
    }


    @PrePersist
    public void persist(Object o) {
//        System.out.println("################### PERSISTE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
