package com.usersevice.user.Services;

import com.usersevice.user.Entities.Product;
import com.usersevice.user.Entities.User;
import com.usersevice.user.Entities.Wishlist;
import com.usersevice.user.Repository.ProductRepository;
import com.usersevice.user.Repository.UserRepository;
import com.usersevice.user.Repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;








//    public void addProductToWishlist(Integer wishlistId, Integer productId) {
//        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
//        if (wishlistOptional.isEmpty()) {
//            throw new IllegalArgumentException("Wishlist not found");
//        }
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isEmpty()) {
//            throw new IllegalArgumentException("Product not found");
//        }
//        Wishlist wishlist = wishlistOptional.get();
//        Product product = productOptional.get();
//        wishlist.getProducts().add(product);
//        wishlistRepository.save(wishlist);
//    }



public void addProductToWishlist(Integer userId, Integer productId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("User not found");
    }

    User user = userOptional.get();

    Wishlist wishlist = user.getWishlist();
    if (wishlist == null) {
        wishlist = new Wishlist();
        wishlist.setUser(user);
    }

    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isEmpty()) {
        throw new IllegalArgumentException("Product not found");
    }

    Product product = productOptional.get();
    wishlist.getProducts().add(product);

    wishlistRepository.save(wishlist);

}


public List<Product> getProductsInWishlistByUserId(Integer userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("User not found");
    }

    User user = userOptional.get();
    Wishlist wishlist = user.getWishlist();
    if (wishlist == null) {
        throw new IllegalStateException("Wishlist not found for user");
    }

    return new ArrayList<>(wishlist.getProducts());
}










//    public void removeProductFromWishlist(Integer wishlistId, Integer productId) {
//        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
//        if (wishlistOptional.isEmpty()) {
//            throw new IllegalArgumentException("Wishlist not found");
//        }
//        Wishlist wishlist = wishlistOptional.get();
//        wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
//        wishlistRepository.save(wishlist);
//    }

    public void deleteProductFromWishlistByUserId(Integer userId, Integer productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        Wishlist wishlist = user.getWishlist();
        if (wishlist != null) {
            Set<Product> products = wishlist.getProducts();
            products.removeIf(product -> product.getId().equals(productId));
            userRepository.save(user); // Save the user to update the changes
        }
    }



}

