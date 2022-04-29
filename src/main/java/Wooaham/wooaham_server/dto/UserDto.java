package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.User;
import lombok.*;
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
    private String token;
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
                user.getToken(),
                user.getRole(),
                user.getCreatedAt(),
                user.getDeletedAt()
        );
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
    public static class RegisterName {
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
        private Long parentId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangeLink {
        private Long studentId;
    }

}
