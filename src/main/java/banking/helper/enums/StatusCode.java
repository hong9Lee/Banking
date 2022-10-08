package banking.helper.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum StatusCode {
    SUCCESS("200", "성공"),
    FAIL("500", "오류"),
    UNKNOWN_DEPOSIT_ACCOUNT("901", "존재하지 않는 입금 계좌 입니다"),
    UNKNOWN_WITHDRAW_ACCOUNT("902", "존재하지 않는 출금 계좌 입니다"),
    LACK_OF_BALANCE("903", "잔액이 부족합니다"),
    TRANSFER_ERROR("904", "이체중에 오류가 발생했습니다");


    private String code;
    private String msg;

    StatusCode(StatusCode status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
