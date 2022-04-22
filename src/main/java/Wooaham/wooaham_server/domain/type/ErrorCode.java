package Wooaham.wooaham_server.domain.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // 400 Bad Request
    INVALID("BR000", "잘못된 요청입니다"),
    INVALID_TYPE("BR001", "잘못된 타입이 입력되었습니다"),

    INVALID_MISSING_PARAMETER("BR100", "필수 파라미터가 입력되지 않았습니다"),
    INVALID_MISSING_AUTH_TOKEN("BR101", "인증 토큰을 입력해주세요"),

    INVALID_AUTH_TOKEN( "BR200", "만료되거나 유효하지 않은 인증 토큰입니다"),

    // 401 UnAuthorized
    UNAUTHORIZED("UA000", "세션이 만료되었습니다. 다시 로그인 해주세요"),

    // 403 Forbidden
    FORBIDDEN( "FB000", "허용하지 않는 요청입니다"),

    // 404 Not Found
    NOTFOUND("NF000", "존재하지 않습니다"),
    NOTFOUND_USER( "NF001", "탈퇴했거나 존재하지 않는 유저입니다"),
    NOTFOUND_ALARM( "NF002", "삭제되었거나 존재하지 않는 알람입니다"),
    NOTFOUND_ICON( "NF003", "존재하지 않는 아이콘입니다"),
    NOTFOUND_NOTICE( "NF004", "존재하지 않는 공지사항입니다"),
    NOTFOUND_TEACHER("NF005", "교사 권한이 없거나 탈퇴한 유저입니다"),
    NOTFOUND_PARENT("NF006", "부모 권한이 없거나 탈퇴한 유저입니다"),
    NOTFOUND_HOMEWORKTYPE("NF007","존재하지 않는 Type입니다\nType은 대문자로 입력해주세요"),


    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED( "MN000", "Not Allowed Method"),

    // 406 Not Acceptable
    NOT_ACCEPTABLE( "NA000", "Not Acceptable"),

    // 409 Conflict
    CONFLICT( "CF000", "이미 존재합니다"),
    CONFLICT_USER("CF001", "이미 해당 계정으로 회원가입되었습니다\n로그인 해주세요"),
    CONFLICT_STUDENT("CF002", "이미 가입한 학생입니다\n학교 정보를 새로 입력해주세요"),
    CONFLICT_TEACHER("CF002", "이미 가입한 교사입니다\n교사인증을 다시 시도해주세요"),
    CONFLICT_READER("CF003", "이미 읽음 표시한 글입니다"),

    // 500 Internal Server Exception
    INTERNAL_SERVER("IS000", "예상치 못한 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),

    // 502 Bad Gateway
    BAD_GATEWAY("BG000", "일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),

    // 503 Service UnAvailable
    SERVICE_UNAVAILABLE("SU000", "현재 해당 기능은 점검 중입니다.\n잠시 후 다시 시도해주세요!"),
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
