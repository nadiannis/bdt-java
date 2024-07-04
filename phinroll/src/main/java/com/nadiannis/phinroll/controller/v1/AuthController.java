package com.nadiannis.phinroll.controller.v1;

import com.nadiannis.phinroll.dto.*;
import com.nadiannis.phinroll.dto.user.AuthDto;
import com.nadiannis.phinroll.dto.user.LoginFormDto;
import com.nadiannis.phinroll.dto.user.RegisterFormDto;
import com.nadiannis.phinroll.dto.user.UserDto;
import com.nadiannis.phinroll.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterFormDto body) {
        UserDto data = service.register(body);
        String message = "user registered successfully";

        SuccessResponse<UserDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginFormDto body) {
        AuthDto data = service.login(body);
        String message = "user authenticated successfully";

        SuccessResponse<AuthDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

}
