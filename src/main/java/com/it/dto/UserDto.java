package com.it.dto;

import com.it.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String name;
    String username;
    String status;
    String createAt;

    public UserDto(User user) {
        if (user != null) {
            this.name = user.getName();
            this.username = user.getUsername();
            this.status = user.getStatus();
            this.createAt = user.getCreateAt().toString();
        }
    }
}
