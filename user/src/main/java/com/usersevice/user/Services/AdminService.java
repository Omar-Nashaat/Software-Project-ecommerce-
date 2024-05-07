package com.usersevice.user.Services;

import com.usersevice.user.Entities.Product;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Repository.ProductRepository;
import com.usersevice.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Product> getAllProducts() {
        if(productRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("there is no Products");
        }
        return productRepository.findAll();
    }

    public Product getSpecificProduct(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isEmpty()) {
            throw new IllegalArgumentException("the product does not exist");
        }
        else {
            return p.get();
        }
    }

    public List<User> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("there is no users");
        }
        else
            return userRepository.findAll();
    }
    public String addProduct(Product product) {
        Optional<Product> p = productRepository.findProductByName(product.getName());
        if (p.isPresent()) {
            throw new IllegalArgumentException("the product already exist");
        } else {
            productRepository.save(product);
            return "added successfully";
        }
    }



}
