package banking.helper.exception;

import lombok.Data;

@Data
public class BalanceException extends RuntimeException{

    public BalanceException(String message, String userId, long balance) {
        super(message);
        this.userId = userId;
        this.balance = balance;
    }

    private String userId;
    private long balance;

}
