package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.repository.TestRepository;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
public class TestController {
    private TestService testService;

    public TestController(TestService testService) {this.testService = testService;}

    @GetMapping
    public List<UserDto> getUsers(){
        return testService.getUsers();
    }
}
