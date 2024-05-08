package com.usersevice.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usersevice.user.Entities.Role;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Services.Implementation.EncryptImpl;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    default Optional<User> findByEncryptedEmail(String encryptedEmail) {
        return findByEmail(EncryptImpl.decrypt(encryptedEmail));
    }
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
    boolean existsByEmail(String email);
}
