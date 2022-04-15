package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.type.UserType;
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateIcon {
        private Long iconId;
    }
}
