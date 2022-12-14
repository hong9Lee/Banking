package banking.helper.exception;

import banking.dto.request.UnKnownAccountRequest;
import banking.dto.response.WithdrawResponse;
import banking.helper.enums.StatusCode;
import banking.helper.util.Utils;
import banking.helper.enums.ResponseStatusType;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {
        System.out.println("GLOBAL+_+");
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }


    /**
     * @valid Request 유효성 체크에 통과하지 못하면 MethodArgumentNotValidException 발생.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class})
    public ResponseEntity BadRequestException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage());

        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {

            FieldError field = (FieldError) error;
            String fieldName = field.getField();
            String message = field.getDefaultMessage();

            errorList.add(Utils.getErrMsg(fieldName, message));
        });

        ErrorResponse errResponse = Utils.getErrResponse(errorList,
                httpServletRequest, ResponseStatusType.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }

    /** 사용자 ID 중복 Exception */
    @ExceptionHandler({ DuplicateKeyException.class })
    public ResponseEntity DuplicateUserIdException(DuplicateKeyException e,HttpServletRequest httpServletRequest) {
        log.error(e.getMessage());

        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setField("userId");
        error.setMessage(e.getMessage());
        errorList.add(error);

        ErrorResponse errResponse = Utils.getErrResponse(errorList,
                httpServletRequest, ResponseStatusType.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }


    @ExceptionHandler({
            NullPointerException.class,
            NoSuchElementException.class, // db에 데이터가 없는 경우
    })
    public ResponseEntity InternalServerException(Exception e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage());
        String name = e.getClass().getName();

        List<Error> errorList = new ArrayList<>();
        errorList.add(Utils.getErrMsg(name + "(" + e.getMessage() + ")", Utils.stackTractToString(e)));

        ErrorResponse errResponse = Utils.getErrResponse(errorList,
                httpServletRequest, ResponseStatusType.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);

    }


    @ExceptionHandler({
            UnKnownAccountException.class
    })
    public ResponseEntity UnKnownAccountException(UnKnownAccountException e) {
        log.error(e.getMsg());
        System.out.println("############" + "GLOBAL UnKnownAccountException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                UnKnownAccountRequest.builder()
                .msg(e.getMsg())
                .code(e.getCode())
                .build()
        );
    }

    @ExceptionHandler({
            BalanceException.class
    })
    public ResponseEntity BalanceException(BalanceException e) {
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                WithdrawResponse.builder()
                        .userId(e.getUserId())
                        .code(StatusCode.LACK_OF_BALANCE.getCode())
                        .balance(e.getBalance())
                        .build()
        );

    }


    @ExceptionHandler({
            TransferException.class
    })
    public ResponseEntity TransferException(TransferException e) throws ParseException {
        System.out.println("############" + "GLOBAL TransferException");
        log.error(e.getMsg());
        log.error(e.getCode());
        return null;
    }

}
