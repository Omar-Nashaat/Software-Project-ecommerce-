package com.usersevice.user.Controller;

import com.usersevice.user.Entities.Product;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;


    @GetMapping("/hello")
    public ResponseEntity<String> SayHello() {
        return ResponseEntity.ok("Hello from admin");
    }


    @GetMapping("/get-all-products")
    private List<Product> getAllProducts() {
        return adminService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        adminService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully: " + product.toString());
    }
    @GetMapping("/get-specific-product/{ProdoctId}")
    private Product getSpecificProduct(@PathVariable Integer ProdoctId) {
        return adminService.getSpecificProduct(ProdoctId);
    }
    @GetMapping("/get-all-users")
    private List<User> getAllUsers() {
        return adminService.getAllUsers();
    }


}