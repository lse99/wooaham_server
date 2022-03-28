package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TestService {
    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<UserDto> getUsers(){
        return testRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
