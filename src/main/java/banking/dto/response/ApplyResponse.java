package banking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyResponse {
    private String userId;
    private String publicToken;
    private String privateToken;
}
