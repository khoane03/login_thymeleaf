package com.it.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {
    String name;
    String username;
    String password;
    String confirmPassword;
    String status;
}
