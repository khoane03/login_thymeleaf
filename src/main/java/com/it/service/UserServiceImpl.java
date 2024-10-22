package com.it.service;

import com.it.dto.request.AuthDto;
import com.it.dto.request.RegisterDto;
import com.it.dto.request.UserUpdateDto;
import com.it.dto.response.UserDto;
import com.it.entity.User;
import com.it.exception.AppException;
import com.it.exception.ErrorMessage;
import com.it.repositpry.UserRepository;
import com.it.utils.constant.StatusConstant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public void registerUser(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
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
    public UserDto getInfo(String username) {
        return new UserDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND)));
    }

    @Override
    public Page<UserDto> getAll(int pageIndex, String keyword) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        if(keyword != null && !keyword.isEmpty()) {
            return userRepository.findAllByKeyword(keyword, pageable)
                    .map(UserDto::new);
        }
        return userRepository.findAll(pageable)
                .map(UserDto::new);
    }

    @Override
    public void updateUser(String username, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));

        if (userUpdateDto.getName() != null && !userUpdateDto.getName().isEmpty()) {
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            if (!userUpdateDto.getPassword().equals(userUpdateDto.getConfirmPassword())) {
                throw new AppException(ErrorMessage.PASSWORD_NOT_MATCH);
            }
        }
        userRepository.save(user);
    }

    @Override
    public void disableUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        user.setStatus(StatusConstant.LOCKED.getValue());
        userRepository.save(user);
    }

    @Override
    public void enableUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        user.setStatus(StatusConstant.ACTIVE.getValue());
        userRepository.save(user);
    }
}
