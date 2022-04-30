package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.User;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private IconDto icon;
    private String name;
    private String email;
    private String birth;
    private UserType role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public static UserDto from(User user){
        return new UserDto(
                user.getId(),
                IconDto.from(user.getIcon()),
                user.getName(),
                user.getEmail(),
                user.getBirth(),
                user.getRole(),
                user.getCreatedAt(),
                user.getDeletedAt()
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create{

        @Email(message = "올바르지 않은 이메일 양식입니다.")
        @NotNull(message = "이메일을 입력해주세요.")
        private String email;

        @Size(min=8, max=20, message = "8-20자리 사이의 값을 입력해주세요")
        @NotNull(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "올바른 형식이 아닙니다.")
        private String password;

        @NotNull(message = "닉네임을 입력해주세요.")
        private String name;

        @NotNull(message = "생년월일 입력해주세요.")
        @Pattern(regexp = "^\\d{8}$",
                message = "올바른 형식이 아닙니다. 20220501 형식으로 입력해주세요")
        private String birth;

        @NotNull(message = "권한을 입력해주세요.")
        private UserType role;
    }

    @Getter
    public static class LogIn{
        @Email(message = "올바르지 않은 이메일 양식입니다.")
        @NotNull(message = "이메일을 입력해주세요.")
        private String email;

        @Size(min=8, max=20, message = "8-20자리 사이의 값을 입력해주세요")
        @NotNull(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "올바른 형식이 아닙니다.")
        private String password;
    }

    @Builder
    @Getter
    public static class Child {
        private Long userId;
        private Long studentId;
        private String name;

        public static Child from(Student student){
            return Child.builder()
                    .userId(student.getUser().getId())
                    .studentId(student.getId())
                    .name(student.getUser().getName())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterIcon {
        private Long iconId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRole {
        private UserType role;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePw {

        @Size(min=8, max=20, message = "8-20자리 사이의 값을 입력해주세요")
        @NotNull(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "올바른 형식이 아닙니다.")
        private String currentPw;

        @Size(min=8, max=20, message = "8-20자리 사이의 값을 입력해주세요")
        @NotNull(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "올바른 형식이 아닙니다.")
        private String newPw;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changeName {
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterSchool {
        private String officeCode;
        private String schoolName;
        private String schoolCode;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterClass {
        private Integer grade;
        private Integer classNum;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterLink {
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangeLink {
        private Long studentId;
    }

}
