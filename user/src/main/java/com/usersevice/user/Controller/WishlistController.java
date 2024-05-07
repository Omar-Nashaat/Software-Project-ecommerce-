package com.usersevice.user.Controller;
import com.usersevice.user.Entities.Product;
import com.usersevice.user.Services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;


    @PostMapping("add-to-wishlist/{productId}/{userId}")
    private String addToWishlist(@PathVariable Integer productId  , @PathVariable Integer userId) {
        wishlistService.addProductToWishlist(userId, productId);
        return "success";
    }

    @DeleteMapping("/delete-from-wishlist/{productId}/{userId}")
    private String deleteFromWishlist(@PathVariable Integer productId  , @PathVariable Integer userId) {
        wishlistService.deleteProductFromWishlistByUserId(userId, productId);
        return "success";
    }

    @GetMapping("/get-products-in-wshlist/{userId}")
    public ResponseEntity<List<Product>> getProductsInWishlistByUserId(@PathVariable Integer userId) {
        List<Product> products = wishlistService.getProductsInWishlistByUserId(userId);
        return ResponseEntity.ok(products);
    }


}
