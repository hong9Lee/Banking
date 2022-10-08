package banking.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UnKnownAccountRequest {
    private String msg;
    private String code;
}
