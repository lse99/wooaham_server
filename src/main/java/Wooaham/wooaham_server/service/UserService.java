package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
