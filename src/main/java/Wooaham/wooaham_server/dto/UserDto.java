package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userIdx;
    private String userName;

    public static UserDto from(User user){
        return new UserDto(
                user.getId(),
                user.getName()
        );
    }
}
