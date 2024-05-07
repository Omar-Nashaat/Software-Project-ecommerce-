package com.usersevice.user.Services;

import com.usersevice.user.Repository.CartRepository;
import com.usersevice.user.Repository.ProductRepository;
import com.usersevice.user.Repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Setter
@Getter
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void deleteCartItemById(int id) {
        cartRepository.deleteById(id);
    }

}
