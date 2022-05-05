package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.UserDto.*;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse registerUser(@RequestBody @Valid UserDto.Create userDto){
        return ApiResponse.success(userService.registerUser(userDto));
    }

    @PostMapping("/login")
    public ApiResponse logIn(@RequestBody @Valid UserDto.LogInReq userDto){
        return ApiResponse.success(userService.logIn(userDto));
    }

    @GetMapping("/{userId}")
    public ApiResponse getUser(@PathVariable(name = "userId") Long userId){
        return ApiResponse.success(userService.getUser(userId));
    }

    @GetMapping("/{userId}/children")
    public ApiResponse getChildren(@PathVariable(name = "userId") Long userId){
        return ApiResponse.success(userService.getChildren(userId));
    }

    @PutMapping("/{userId}/password")
    public ApiResponse changePw(@PathVariable(name = "userId") Long userId,
                         @RequestBody @Valid UserDto.ChangePw userDto){
        return ApiResponse.success(userService.changePw(userId, userDto));
    }
    @PutMapping("/{userId}/name")
    public ApiResponse changeName(@PathVariable(name = "userId") Long userId,
                           @RequestBody UserDto.changeName userDto){
       return ApiResponse.success(userService.changeName(userId, userDto));
    }

    @PutMapping("/{userId}/school")
    public ApiResponse registerSchool(@PathVariable(name = "userId") Long userId,
                                 @RequestBody UserDto.RegisterSchool userDto){
        return ApiResponse.success(userService.registerSchool(userId, userDto));
    }

    @PutMapping("/{userId}/class")
    public ApiResponse registerClass(@PathVariable(name = "userId") Long userId,
                               @RequestBody UserDto.RegisterClass userDto){
        return ApiResponse.success(userService.registerClass(userId, userDto));
    }

    @PutMapping("/{userId}/link")
    public ApiResponse registerLink(@PathVariable(name = "userId") Long userId,
                     @RequestBody UserDto.RegisterLink userDto){
        return ApiResponse.success(userService.registerLink(userId, userDto));
    }

    @PutMapping("/{userId}/link/change")
    public ApiResponse changeLink(@PathVariable(name = "userId") Long userId,
                           @RequestBody UserDto.ChangeLink userDto){
        return ApiResponse.success(userService.changeLink(userId, userDto));
    }

    @PutMapping("/{userId}")
    public ApiResponse deleteUser(@PathVariable(name = "userId") Long userId){
        return ApiResponse.success(userService.deleteUser(userId));
    }
}
