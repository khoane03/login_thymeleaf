package com.it.service;

import com.it.dto.AuthDto;
import com.it.dto.RegisterDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public void registerUser(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new AppException(ErrorMessage.USER_EXISTS);
        }
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new AppException(ErrorMessage.PASSWORD_NOT_MATCH);
        }
        userRepository.save(User.builder()
                .name(registerDto.getName())
                .username(registerDto.getUsername())
                .password(registerDto.getPassword())
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

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .toList();
    }
}
