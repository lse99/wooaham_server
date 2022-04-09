package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.IconRepository;
import Wooaham.wooaham_server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final IconRepository iconRepository;

    public UserService(UserRepository userRepository, IconRepository iconRepository) {
        this.userRepository = userRepository;
        this.iconRepository = iconRepository;
    }

    public List<UserDto> getUsers(){
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto getUser(Long userId){
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElseThrow();
    }

    public void updateUserIcon(Long userId, UserDto.UpdateIcon userDto){
        User user = userRepository.findById(userId).orElseThrow();
        Icon icon = iconRepository.findById(userDto.getIconId()).orElseThrow();

        user.setIconId(icon.getIconId());
        userRepository.save(user);
    }
}
