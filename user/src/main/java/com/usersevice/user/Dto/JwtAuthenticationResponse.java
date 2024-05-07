package com.usersevice.user.Dto;

import com.usersevice.user.Entities.Role;

public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private Integer userId; 
    private Role role; 

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer integer) {
        this.userId = integer;
    }
}
