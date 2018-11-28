package com.server.empathy.controller;


import com.server.empathy.dto.in.user.CreateUserDto;
import com.server.empathy.dto.in.user.UpdateUserDto;
import com.server.empathy.dto.out.journey.GetJourneySimpleDto;
import com.server.empathy.dto.out.user.GetUserDto;
import com.server.empathy.exception.BaseException;
import com.server.empathy.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({ "/user/" , "/user" })
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    public void createUser(
            @Valid @RequestBody CreateUserDto dto,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BaseException();
        userService.createUser(dto);
    }

    @GetMapping("/info/{userId}")
    public GetUserDto getUserInfo(
            @PathVariable(name = "userId") String targetId
    ){
        Long userId = Long.parseLong(targetId);
        return userService.getUserInfo(userId);
    }

    @PatchMapping("/info")
    public void patchUserInfo(
            @Valid @RequestBody UpdateUserDto dto
    ){
        userService.updateUser(dto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(
            @PathVariable(name = "userId") String userId
    ){
        Long targetId = Long.parseLong(userId);
        userService.deleteUser(targetId);
    }
}
