package com.usersevice.user.Services.Implementation;

import com.usersevice.user.Entities.Product;
import com.usersevice.user.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usersevice.user.Repository.UserRepository;
import com.usersevice.user.Services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               return (UserDetails) userRepository.findByEmail(username)
                       .orElseThrow(()->new UsernameNotFoundException("User not found"));
            }
        };
    }
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


    public long countProducts() {
        return productRepository.count(); 
    }
    
    public String hello(){
        return "hello";
    }
}
