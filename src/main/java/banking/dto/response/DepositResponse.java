package banking.dto.response;

import banking.helper.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {

    private String publicToken;
    private String code;


}
