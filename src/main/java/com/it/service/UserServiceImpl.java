package com.it.service;

import com.it.dto.AuthDto;
import com.it.dto.UserDto;
import com.it.entity.User;
import com.it.exception.AppException;
import com.it.exception.ErrorMessage;
import com.it.repositpry.UserRepository;
import com.it.utils.constant.StatusConstant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public void save(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new AppException(ErrorMessage.USER_EXISTS);
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new AppException(ErrorMessage.PASSWORD_NOT_MATCH);
        }
        userRepository.save(User.builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .status(StatusConstant.ACTIVE.getValue())
                .build());
    }

    @Override
    public void login(AuthDto authDto) {

        User user = userRepository.findByUsernameAndPassword(authDto.getUsername(), authDto.getPassword())
                .orElseThrow(() -> new AppException(ErrorMessage.USERNAME_PASSWORD_INCORRECT));

        if (!user.getStatus().equalsIgnoreCase(StatusConstant.ACTIVE.getValue())) {
            throw new AppException(ErrorMessage.USER_NOT_ACTIVE);
        }
    }
}
