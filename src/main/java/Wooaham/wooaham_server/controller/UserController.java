package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody @Valid UserDto.Create userDto){
        userService.registerUser(userDto);
    }

    @GetMapping("/login")
    public Long logIn(@RequestBody @Valid UserDto.LogIn userDto){
        return userService.logIn(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable(name = "userId") Long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/children")
    public List<UserDto.Child> getChildren(@PathVariable(name = "userId") Long userId){
        return userService.getChildren(userId);
    }

    @PutMapping("/{userId}/password")
    public void changePw(@PathVariable(name = "userId") Long userId,
                         @RequestBody @Valid UserDto.ChangePw userDto){
        userService.changePw(userId, userDto);
    }
    @PutMapping("/{userId}/name")
    public void changeName(@PathVariable(name = "userId") Long userId,
                           @RequestBody UserDto.changeName userDto){
        userService.changeName(userId, userDto);
    }

    @PutMapping("/{userId}/school")
    public void registerSchool(@PathVariable(name = "userId") Long userId,
                                 @RequestBody UserDto.RegisterSchool userDto){
        userService.registerSchool(userId, userDto);
    }

    @PutMapping("/{userId}/class")
    public void registerClass(@PathVariable(name = "userId") Long userId,
                               @RequestBody UserDto.RegisterClass userDto){
        userService.registerClass(userId, userDto);
    }

    @PutMapping("/{userId}/link")
    public void registerLink(@PathVariable(name = "userId") Long userId,
                     @RequestBody UserDto.RegisterLink userDto){
        userService.registerLink(userId, userDto);
    }

    @PutMapping("/{userId}/link/change")
    public void changeLink(@PathVariable(name = "userId") Long userId,
                           @RequestBody UserDto.ChangeLink userDto){
        userService.changeLink(userId, userDto);
    }

    @PutMapping("/{userId}")
    public void deleteUser(@PathVariable(name = "userId") Long userId){
        userService.deleteUser(userId);
    }
}
