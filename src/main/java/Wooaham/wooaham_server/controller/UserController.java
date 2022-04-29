package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable(name = "userId") Long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/children")
    public List<UserDto.Child> getChildren(@PathVariable(name = "userId") Long userId){
        return userService.getChildren(userId);
    }

    @PutMapping("/{userId}/name")
    public void registerName(@PathVariable(name = "userId") Long userId,
                                 @RequestBody UserDto.RegisterName userDto){
        userService.registerName(userId, userDto);
    }

    @PutMapping("/{userId}/role")
    public void registerRole(@PathVariable(name = "userId") Long userId,
                                 @RequestBody UserDto.RegisterRole userDto){
        userService.registerRole(userId, userDto);
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
