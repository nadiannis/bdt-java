package com.nadiannis.phinroll.service.impl;

import com.nadiannis.phinroll.security.JwtService;
import com.nadiannis.phinroll.dto.user.AuthDto;
import com.nadiannis.phinroll.dto.user.LoginFormDto;
import com.nadiannis.phinroll.dto.user.RegisterFormDto;
import com.nadiannis.phinroll.dto.user.UserDto;
import com.nadiannis.phinroll.exception.ResourceAlreadyExistsException;
import com.nadiannis.phinroll.model.Role;
import com.nadiannis.phinroll.model.User;
import com.nadiannis.phinroll.repository.UserRepository;
import com.nadiannis.phinroll.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto register(RegisterFormDto registerFormDto) {
        Optional<User> user = repository.findByUsername(registerFormDto.getUsername());
        if (user.isPresent()) {
            throw new ResourceAlreadyExistsException("user", "username", registerFormDto.getUsername());
        }

        User newUser = new User();
        newUser.setName(registerFormDto.getName());
        newUser.setUsername(registerFormDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerFormDto.getPassword()));
        newUser.setRole(Role.ADMIN);

        return mapToUserDto(repository.save(newUser));
    }

    @Override
    public AuthDto login(LoginFormDto loginFormDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginFormDto.getUsername(),
                        loginFormDto.getPassword()
                )
        );
        User user = repository
                .findByUsername(loginFormDto.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return mapToAuthDto(token);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private AuthDto mapToAuthDto(String token) {
        AuthDto authDto = new AuthDto();
        authDto.setToken(token);
        return authDto;
    }

}
