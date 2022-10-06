package banking.helper.util;

import banking.helper.enums.ResponseStatusType;
import banking.helper.exception.Error;
import banking.helper.exception.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class Utils {

    /** 에러 상태값 ErrorResponse DTO 형태로 SET */
    public static ErrorResponse getErrResponse(List<Error> errorList,
                                               HttpServletRequest httpServletRequest,
                                               ResponseStatusType resType) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setMessage(resType.getMessage());
        errorResponse.setStatus(resType.getCode());
        return errorResponse;
    }

    /** errorList에 들어갈 에러 메시지 SET */
    public static Error getErrMsg(String field, String msg) {
        Error errorMsg = new Error();
        errorMsg.setField(field);
        errorMsg.setMessage(msg);

        return errorMsg;
    }

    /** Exception의 printStackTrace.toString()을 생성 */
    public static String stackTractToString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
