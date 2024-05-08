package com.usersevice.user.Repository;

import com.usersevice.user.Entities.Cart;
import com.usersevice.user.Entities.Product;
import com.usersevice.user.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);
    @Query("SELECT c.product FROM Cart c WHERE c.user.id = ?1")
    List<Product> findProductsInCartByUserId(Integer userId);
    Cart findByUserAndProduct(User user, Product product);
}
