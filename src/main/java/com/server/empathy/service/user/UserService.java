package com.server.empathy.service.user;

import com.server.empathy.dto.in.user.CreateUserDto;
import com.server.empathy.dto.in.user.UpdateUserDto;
import com.server.empathy.dto.out.user.GetUserDto;

public interface UserService {
    Long createUser(CreateUserDto dto);
    GetUserDto getUserInfo(Long userId);
    void updateUser(UpdateUserDto dto);
    void deleteUser(Long userId);

}
