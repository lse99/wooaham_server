package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.type.ErrorCode;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;

    private T data;

    private ErrorCode error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode)  {
        return new ApiResponse<T>(false , null, errorCode);
    }
}
