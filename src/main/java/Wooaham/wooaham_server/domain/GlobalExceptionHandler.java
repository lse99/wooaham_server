package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "Wooaham.wooaham_server")
public class GlobalExceptionHandler {

    /**
     * dayrider biz exception handler
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException be) {

        ErrorCode errorCode = be.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> handleException(Throwable e) {

        log.error("[GlobalExceptionHandler]", e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER.getStatus())
                .body(ErrorResponse.builder()
                        .status(ErrorCode.INTERNAL_SERVER.getStatus())
                        .code(ErrorCode.INTERNAL_SERVER.getCode())
                        .message(ErrorCode.INTERNAL_SERVER.getMessage())
                        .build());
    }

}
