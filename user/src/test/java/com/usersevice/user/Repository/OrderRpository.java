package com.usersevice.user.Repository;

import com.usersevice.user.Entities.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRpository  extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);


}
