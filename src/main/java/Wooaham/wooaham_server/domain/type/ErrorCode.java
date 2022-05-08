package Wooaham.wooaham_server.domain.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // 400 Bad Request
    INVALID(400,"BR000", "잘못된 요청입니다"),
    INVALID_TYPE(400,"BR001", "잘못된 타입이 입력되었습니다"),

    INVALID_MISSING_PARAMETER(400,"BR100", "필수 파라미터가 입력되지 않았습니다"),
    INVALID_MISSING_AUTH_TOKEN(400,"BR101", "인증 토큰을 입력해주세요"),

    INVALID_AUTH_TOKEN( 400,"BR200", "만료되거나 유효하지 않은 인증 토큰입니다"),
    INVALID_ROLE_TYPE(400,"IV001", "올바르지 않은 권한입니다"),
    INVALID_ROLE_FOR_SCHOOL(400,"IV002", "학교 정보 등록이 불가한 권한입니다"),
    INVALID_ROLE_FOR_CLASS(400,"IV003", "반 정보 등록이 불가한 권한입니다"),
    INVALID_EMAIL(400,"IV004", "올바르지 않은 이메일 형식입니다"),
    INVALID_PASSWORD(400,"IV005", "올바르지 않은 비밀번호 형식입니다"),
    INVALID_PASSWORD_SIZE(400,"IV006", "8-20자리 사이의 값을 입력해주세요"),
    INVALID_BIRTH(400,"IV007", "올바른 형식이 아닙니다. 20220501 형식으로 입력해주세요"),

    EMPTY_EMAIL(400,"EP000", "이메일을 입력해주세요"),
    EMPTY_PASSWORD(400,"EP001", "비밀번호를 입력해주세요"),
    EMPTY_NAME(400,"EP002", "닉네임을 입력해주세요"),
    EMPTY_BIRTH(400,"EP003", "생년월일을 입력해주세요"),
    EMPTY_ROLE(400,"EP004", "역할을 선택해주세요"),

    // 401 UnAuthorized
    UNAUTHORIZED(401,"UA000", "세션이 만료되었습니다. 다시 로그인 해주세요"),
    PASSWORD_ENCRYPTION_ERROR(401,"UA001","비밀번호 암호화에 실패하였습니다"),
    PASSWORD_DECRYPTION_ERROR(401,"UA002","비밀번호 복호화에 실패하였습니다"),
    FAILED_TO_LOGIN(401,"UA003", "없는 아이디거나 비밀번호가 틀렸습니다"),
    PASSWORD_CHANGE_ERROR(401,"UA004", "비밀번호 변경에 실패하였습니다"),
    EMPTY_JWT(401,"UA005", "JWT를 입력해주세요"),
    INVALID_JWT(401,"UA006","유효하지 않은 JWT입니다"),
    INVALID_USER_JWT(401,"UA007", "권한이 없는 유저의 접근입니다"),

    // 403 Forbidden
    FORBIDDEN( 403,"FB000", "허용하지 않는 요청입니다"),

    // 404 Not Found
    NOTFOUND(404,"NF000", "존재하지 않습니다"),
    NOTFOUND_USER( 404,"NF001", "탈퇴했거나 존재하지 않는 유저입니다"),
    NOTFOUND_ALARM( 404,"NF002", "삭제되었거나 존재하지 않는 알람입니다"),
    NOTFOUND_ICON( 404,"NF003", "존재하지 않는 아이콘입니다"),
    NOTFOUND_NOTICE( 404,"NF004", "존재하지 않는 공지사항입니다"),
    NOTFOUND_TEACHER(404,"NF005", "교사 권한이 없거나 탈퇴한 유저입니다"),
    NOTFOUND_PARENT(404,"NF006", "부모 권한이 없거나 탈퇴한 유저입니다"),
    NOTFOUND_STUDENT(404,"NF007", "학생 권한이 없거나 탈퇴한 유저입니다"),
    NOTFOUND_HOMEWORKTYPE(404,"NF008","존재하지 않는 Type입니다\nType은 대문자로 입력해주세요"),
    NOTFOUND_HOMEWORK( 404,"NF009", "존재하지 않는 숙제입니다"),
    NOTFOUND_CHILDREN(404,"NF010", "연결된 자녀가 없습니다"),
    NOT_FOUND_SCHOOL(404,"NF011", "학교 정보를 등록해주세요"),


    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED( 405,"MN000", "Not Allowed Method"),

    // 406 Not Acceptable
    NOT_ACCEPTABLE( 406,"NA000", "Not Acceptable"),

    // 409 Conflict
    CONFLICT( 409,"CF000", "이미 존재합니다"),
    CONFLICT_USER(409,"CF001", "이미 해당 계정으로 회원가입되었습니다\n로그인 해주세요"),
    CONFLICT_STUDENT(409,"CF002", "이미 가입한 학생입니다\n학교 정보를 새로 입력해주세요"),
    CONFLICT_TEACHER(409,"CF002", "이미 가입한 교사입니다\n교사인증을 다시 시도해주세요"),
    CONFLICT_READER(409,"CF003", "이미 읽음 표시한 글입니다"),
    CONFLICT_USER_DELETED(409,"CF004", "이미 탈퇴한 계정입니다"),
    CONFLICT_USER_ROLE(409,"CF005", "이미 권한이 등록되어 있습니다"),
    CONFLICT_LINK(409,"CF006", "이미 부모님과 연결되어 있습니다"),

    // 500 Internal Server Exception
    INTERNAL_SERVER(500, "IS000", "예상치 못한 에러가 발생하였습니다"),

    // 502 Bad Gateway
    BAD_GATEWAY(502, "BG000", "일시적인 에러가 발생하였습니다"),

    // 503 Service UnAvailable
    SERVICE_UNAVAILABLE(503,"SU000", "현재 해당 기능은 점검 중입니다"),
    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
