package com.nadiannis.phinroll.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormDto {

    @NotBlank(message = "username is required")
    private String username;

    @NotEmpty(message = "password is required")
    private String password;

}
