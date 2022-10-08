package banking.helper.exception;

import banking.helper.enums.StatusCode;
import lombok.Data;

@Data
public class UnKnownAccountException extends RuntimeException{

    public UnKnownAccountException(StatusCode status) {
        this.status = status;
        this.msg = status.getMsg();
        this.code = status.getCode();
    }

    private String msg;
    private String code;
    private StatusCode status;


}
