package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.user.User;
//import Wooaham.wooaham_server.domain.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private Long userId;

    @Nullable
    private IconDto icon;

    private String name;

    private String email;

    private String birth;

    private String token;

    //private RoleType role;

    private LocalDateTime createdAt;

    public static UserDto from(User user){
        return new UserDto(
                user.getId(),
                IconDto.from(user.getIcon()),
                user.getName(),
                user.getEmail(),
                user.getBirth(),
                user.getToken(),
                //user.getRole(),
                user.getCreatedAt()
        );
    }
}
