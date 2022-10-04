package banking.helper.exception;

import lombok.Data;

@Data
public class Error {

    private String field;
    private String message;

}
