package com.usersevice.user.Services;

import org.springframework.http.ResponseEntity;

import com.usersevice.user.Dto.JwtAuthenticationResponse;
import com.usersevice.user.Dto.RefreshTokenRequest;
import com.usersevice.user.Dto.SignUpRequest;
import com.usersevice.user.Dto.SigninRequest;

public interface AuthService {
   ResponseEntity<?> SignUp(SignUpRequest signUpRequest);
   ResponseEntity<?> SignIn(SigninRequest signinRequest);
    JwtAuthenticationResponse RefreshToken (RefreshTokenRequest refreshTokenRequest);
}
