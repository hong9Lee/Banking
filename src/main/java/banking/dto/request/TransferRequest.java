package banking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "형식에 맞지 않는 public token 입니다.")
    @NotNull(message = "publicToken is Null")
    private String publicToken;

    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "형식에 맞지 않는 private token 입니다.")
    @NotNull(message = "publicToken is Null")
    private String privateToken;

    @Min(value = 1, message = "출금 금액은 1원 이상이여야 합니다.")
    @NotNull(message = "money is Null")
    private long money;
}
