package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
        private String email;
        private String password;
        private String name;
        private String birth;
        private UserType role;
    }

    @Getter
    public static class LogInReq {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LogInRes{
        private Long userId;
        private String jwt;
    }

    @Getter
    public static class UserInfo{
        private Long userId;
        private UserType role;

        public UserInfo(Long userId, String role) {
            this.userId = userId;
            this.role = UserType.valueOf(role);
        }
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
        private String currentPw;
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
