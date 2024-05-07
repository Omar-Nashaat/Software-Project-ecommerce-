package com.usersevice.user.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Admin {
    private int id;
    private String username;
    private String password;
    private String email;

}
