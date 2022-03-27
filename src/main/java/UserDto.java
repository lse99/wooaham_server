import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userIdx;

    public static UserDto from(User user){
        return new UserDto(
                user.getId()
        );
    }
}
