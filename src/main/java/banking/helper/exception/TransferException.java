package banking.helper.exception;

import banking.helper.enums.StatusCode;
import lombok.Data;

@Data
public class TransferException extends RuntimeException {

    public TransferException(StatusCode status) {
        this.msg = status.getMsg();
        this.code = status.getCode();
    }

    private String msg;
    private String code;
}
