package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.user.AuthDto;
import com.nadiannis.phinroll.dto.user.LoginFormDto;
import com.nadiannis.phinroll.dto.user.RegisterFormDto;
import com.nadiannis.phinroll.dto.user.UserDto;

public interface AuthService {

    UserDto register(RegisterFormDto registerFormDto);
    AuthDto login(LoginFormDto loginFormDto);

}
