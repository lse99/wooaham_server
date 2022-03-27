import lombok.Getter;
import org.aspectj.weaver.ast.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class TestController {
    private TestRepository testRepository;

    public TestController(TestRepository testRepository) {this.testRepository = testRepository;}

    @GetMapping
    public List<UserDto> getUsers(){
        return testRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
