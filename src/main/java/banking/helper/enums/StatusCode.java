package banking.helper.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum StatusCode {
    SUCCESS("200"),
    FAIL("500");


    private String code;

    StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
