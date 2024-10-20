package com.it.service;

import com.it.dto.AuthDto;
import com.it.dto.RegisterDto;
import com.it.dto.UserDto;

import java.util.List;

public interface UserService {
    void registerUser(RegisterDto registerDto);
    void login(AuthDto auth);
    List<UserDto> getAll();
}
