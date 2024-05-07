package com.usersevice.user.Controller;

import com.usersevice.user.Entities.Product;
import com.usersevice.user.Services.Implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
     final UserServiceImplementation userServiceImplementation;
    @GetMapping
    public ResponseEntity<String> SayHello(){
        return ResponseEntity.ok("Hello from user");
    }
    @GetMapping("/get-all-products")
    private List<Product> getAllProducts(){
        return userServiceImplementation.getAllProducts();
    }
    @GetMapping("/get-specific-product/ProdoctId")
    private Product getSpecificProduct(@PathVariable Integer ProdoctId) {
        return userServiceImplementation.getSpecificProduct(ProdoctId);
    }
    @GetMapping("/count-products")
    public ResponseEntity<Long> countProducts() {
        long productCount = userServiceImplementation.countProducts();
        return ResponseEntity.ok(productCount);
    }



}
