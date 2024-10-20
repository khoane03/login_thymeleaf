package com.it.service;

import com.it.dto.AuthDto;
import com.it.dto.UserDto;

public interface UserService {
    void save(UserDto userDto);
    void login(AuthDto auth);
}
