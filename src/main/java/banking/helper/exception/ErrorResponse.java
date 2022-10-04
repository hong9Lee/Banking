package banking.helper.exception;

import lombok.Data;
import java.util.List;

@Data
public class ErrorResponse {
    String status;
    String message;
    String requestUrl;
    List<Error> errorList;

}
