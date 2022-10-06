package banking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequest {
    @NotNull(message = "id는 필수값입니다.")
    @Size(min = 4)
    private String userId;

}
