package com.usersevice.user.Repository;

import com.usersevice.user.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByName(String name);
      Product findById(long id);
    // Double getProductPriceById(Integer id);
}
