package com.usersevice.user.Controller;

import com.usersevice.user.Entities.Cart;
import com.usersevice.user.Entities.Product;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Repository.CartRepository;
import com.usersevice.user.Repository.ProductRepository;
import com.usersevice.user.Repository.UserRepository;
import com.usersevice.user.Services.CartService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@RequestMapping("/cart")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
//    @Autowired
//    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/addToCart/{userId}/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Integer productId, @PathVariable Integer userId) {
        User user;
        user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Check if the product quantity is available
        if (product.getQuantityLeft() <= 0) {
            return ResponseEntity.badRequest().body("Product is out of stock");
        }
        Cart cartItem = new Cart();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setName(product.getName());
        cartItem.setDescription(product.getDescription());
        cartItem.setPrice(product.getPrice());
        cartItem.setRating(product.getRating());
        cartItem.setQuantityLeft(product.getQuantityLeft());
        cartRepository.save(cartItem);
        return ResponseEntity.ok("Product added to cart successfully");
    }


    @DeleteMapping("/deleteCartItem/{id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable Integer id) {
        try {
            cartService.deleteCartItemById(id);
            return ResponseEntity.ok("Cart item deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/clearCart")
    public ResponseEntity<String> clearCart() {
        try {
            cartRepository.deleteAll();
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getCart/{userId}")
    public ResponseEntity<List<Product>> getCart(@PathVariable Integer userId) {
        List<Product> productsInCart = cartRepository.findProductsInCartByUserId(userId);

        if (productsInCart.isEmpty()) {
            return ResponseEntity.notFound().build(); // No products in cart found for the user
        }

        return ResponseEntity.ok(productsInCart); // Return the products in the cart
    }

}

