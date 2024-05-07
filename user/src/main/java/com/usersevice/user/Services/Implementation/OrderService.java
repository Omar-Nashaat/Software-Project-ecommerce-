package com.usersevice.user.Services.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usersevice.user.Dto.OrderItemRequest;
import com.usersevice.user.Entities.Order;
import com.usersevice.user.Entities.OrderItem;
import com.usersevice.user.Entities.Product;
import com.usersevice.user.Repository.OrderRpository;
import com.usersevice.user.Repository.ProductRepository;

import java.util.Date;
// import com.usersevice.user.Repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRpository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    
    public Order createOrder(Long userId, String paymentMethod, List<OrderItemRequest> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setPaymentMethod(paymentMethod);
        order.setCreationDate(new Date());

        List<OrderItem> items = orderItems.stream()
                .map(itemDto -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProductId(itemDto.getProductId());
                    orderItem.setQuantity(itemDto.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(items);

        // Calculate total price based on order items (assuming each order item has a price)
        double totalPrice = items.stream()
                .mapToDouble(item -> item.getQuantity() * getItemPrice(item.getProductId()))
                .sum();

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private double getItemPrice(Integer productId) {
    //  return productRepository.getProductPriceById(productId);
      return getProductPriceById(productId.longValue());
    }

    
    public double getProductPriceById(Long productId) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            return product.getPrice();
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }
    public long getTotalOrders() {
        return orderRepository.count();
    }
}