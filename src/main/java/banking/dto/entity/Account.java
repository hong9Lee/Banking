package banking.dto.entity;

import banking.helper.listener.BaseTimeEntity;
import banking.helper.listener.TransferListener;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {TransferListener.class})
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends BaseTimeEntity {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;
    private String publicToken;
    private String privateToken;
    private long money = 0;

    @Transient
    private long calcMoney;

    @OneToMany(fetch = FetchType.LAZY
            , mappedBy = "account"
            , cascade = {CascadeType.ALL}
            , orphanRemoval = true)
    @ToString.Exclude
    private List<AccountHistory> accountHistoryList = new ArrayList<>();

    public void addCommunityItem(AccountHistory... accountHistory) {
        Collections.addAll(this.accountHistoryList, accountHistory);
    }
}
