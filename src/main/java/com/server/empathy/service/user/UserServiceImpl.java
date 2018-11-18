package com.server.empathy.service.user;

import com.server.empathy.dto.in.user.CreateUserDto;
import com.server.empathy.dto.in.user.UpdateUserDto;
import com.server.empathy.dto.out.user.GetUserDto;
import com.server.empathy.entity.User;
import com.server.empathy.exception.NotFoundException;
import com.server.empathy.repository.UserRepository;
import com.server.empathy.service.TimeStampUtil;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(CreateUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .loginApi( dto.getLoginApi() != null? dto.getLoginApi() : "basic")
                .profileUrl( dto.getProfileUrl() != null? dto.getProfileUrl() : "URL")
                .build();

        userRepository.save(user);
    }

    @Override
    public GetUserDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException());

        GetUserDto result = GetUserDto.builder()
                .userId(user.getUserId())
                .creationTime(TimeStampUtil.stampFormatSimple(user.getCreationDate()))
                .loginApi(user.getLoginApi())
                .name(user.getName())
                .profileUrl(user.getProfileUrl())
                .build();

        return result;
    }

    @Override
    public void updateUser(UpdateUserDto dto) {
        User targetUser = userRepository.findById(dto.getTargetId())
                .orElseThrow(NotFoundException::new);

        targetUser.setName(dto.getName());
        userRepository.save(targetUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
