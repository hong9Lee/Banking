package banking.dto.entity;

import banking.helper.listener.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransferHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private long id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    @ToString.Exclude
//    private Account depositAccount;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    @ToString.Exclude
//    private Account withdrawAccount;

    private String depositAccount;
    private String withdrawAccount;

    private long money; // 전달 금액
//
//    private long

    private String transferKey;

    private String status;
    private String msg;

}
