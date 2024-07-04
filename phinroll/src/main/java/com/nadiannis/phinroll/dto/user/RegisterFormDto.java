package com.nadiannis.phinroll.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFormDto {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[a-zA-Z0-9@._+-]*$", message = "username can only contain letters, numbers, & characters @/./+/-/_")
    private String username;

    @NotEmpty(message = "password is required")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

}
