package com.usersevice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.usersevice.user.Entities.Role;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Repository.UserRepository;
import com.usersevice.user.Services.Implementation.EncryptImpl;

@SpringBootApplication
public class UserApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	public void run(String... args){
         User adminAccount = userRepository.findByRole(Role.Admin);
		 if (null == adminAccount){
			 User user = new User();
			 user.setEmail(EncryptImpl.encrypt("karim@gmail.com"));
			 user.setFirstname("admin");
			 user.setLastname("admin");
			 user.setRole(Role.Admin);
			 user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			 userRepository.save(user);
		 }
	}
}
