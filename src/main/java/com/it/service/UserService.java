package com.it.service;

import com.it.dto.request.AuthDto;
import com.it.dto.request.RegisterDto;
import com.it.dto.request.UserUpdateDto;
import com.it.dto.response.UserDto;
import org.springframework.data.domain.Page;



public interface UserService {
    void registerUser(RegisterDto registerDto);
    void updateUser(String username, UserUpdateDto userUpdateDto);
    void login(AuthDto auth);
    void disableUser(String username);
    void enableUser(String username);
    UserDto getInfo(String username);
    Page<UserDto> getAll(int pageIndex, String keyword);
}
