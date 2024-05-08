package com.usersevice.user.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersevice.user.Dto.JwtAuthenticationResponse;
import com.usersevice.user.Dto.RefreshTokenRequest;
import com.usersevice.user.Dto.SignUpRequest;
import com.usersevice.user.Dto.SigninRequest;
import com.usersevice.user.Services.AuthService;


@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?>signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authService.SignUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<?>signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authService.SignIn(signinRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse>refresh (@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.RefreshToken(refreshTokenRequest));
    }
}
