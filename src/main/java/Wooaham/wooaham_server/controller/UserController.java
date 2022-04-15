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
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable(name = "id") Long userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{id}/role")
    public void registerUserRole(@PathVariable(name = "id") Long userId,
                                 @RequestBody UserDto.RegisterRole userDto){
        userService.registerUserRole(userId, userDto);
    }

    @PutMapping("/{id}/icon")
    public void updateUserIcon(@PathVariable(name = "id") Long userId,
                           @RequestBody UserDto.UpdateIcon userDto) {
        userService.updateUserIcon(userId, userDto);
    }

    @PutMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userId){
        userService.deleteUser(userId);
    }
}
