package Wooaham.wooaham_server.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    Integer status;
    String code;
    String message;
    LocalDateTime timestamp;

    @Builder
    public ErrorResponse(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
