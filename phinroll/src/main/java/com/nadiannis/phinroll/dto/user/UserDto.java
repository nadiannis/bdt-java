package com.nadiannis.phinroll.dto.user;

import com.nadiannis.phinroll.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private String username;
    private Role role;

}
