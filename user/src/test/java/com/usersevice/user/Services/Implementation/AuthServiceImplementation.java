package com.usersevice.user.Services.Implementation;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersevice.user.Dto.ErrorResponse;
import com.usersevice.user.Dto.JwtAuthenticationResponse;
import com.usersevice.user.Dto.RefreshTokenRequest;
import com.usersevice.user.Dto.SignUpRequest;
import com.usersevice.user.Dto.SigninRequest;
import com.usersevice.user.Entities.Role;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Repository.UserRepository;
import com.usersevice.user.Services.AuthService;
import com.usersevice.user.Services.JWTService;

import jakarta.validation.Valid;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
@Autowired
    private UserRepository userRepository;
@Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private JWTService jwtService;

    public ResponseEntity<?> SignUp(@Valid SignUpRequest signUpRequest) {
        try {
            String encryptedEmail = EncryptImpl.encrypt(signUpRequest.getEmail());
            
            // Check if encrypted email already exists
            if (userRepository.existsByEmail(encryptedEmail)) {
                // If email already exists, return a custom error response
                ErrorResponse errorResponse = new ErrorResponse("Email already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
    
            // Email doesn't exist, proceed with user registration
            User user = new User();
            user.setEmail(encryptedEmail);
            user.setFirstname(signUpRequest.getFirstname());
            user.setLastname(signUpRequest.getLastname());
            user.setRole(Role.Customer);
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userRepository.save(user);
    
            // Return success response
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            // Catching any unexpected errors
            ErrorResponse errorResponse = new ErrorResponse("An error occurred during sign-up");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    
    

    public ResponseEntity<?> SignIn(@Valid SigninRequest signinRequest) {
    try {
        // Decrypt the email before querying the database
        String encryptedEmail = EncryptImpl.encrypt(signinRequest.getEmail());
        var user = userRepository.findByEmail(encryptedEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        // Authenticate user
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), signinRequest.getPassword()));
        } catch (AuthenticationException e) {
            // If authentication fails, return a custom error response
            ErrorResponse errorResponse = new ErrorResponse("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Generate tokens
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        // Create JwtAuthenticationResponse object
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
            // Set user ID in the response
        jwtAuthenticationResponse.setUserId(user.getId()); 
        jwtAuthenticationResponse.setRole(user.getRole());

        // Return the success response
        return ResponseEntity.ok(jwtAuthenticationResponse);
    } catch (IllegalArgumentException e) {
        // Catching IllegalArgumentException from findByEmail() or authentication failure
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception e) {
        // Catching any other unexpected errors
        ErrorResponse errorResponse = new ErrorResponse("An error occurred during login");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

public JwtAuthenticationResponse RefreshToken (RefreshTokenRequest refreshTokenRequest){
    String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
    User user = userRepository.findByEncryptedEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
    if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user))
    {
        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        return jwtAuthenticationResponse;
    }
    return null;
}

}
