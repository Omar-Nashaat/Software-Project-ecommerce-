package com.usersevice.user.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.usersevice.user.Dto.OrderRequestDto;
import com.usersevice.user.Entities.Order;
import com.usersevice.user.Services.Implementation.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequestDto request) {
        return orderService.createOrder(request.getUserId(), request.getPaymentMethod(), request.getOrderItems());
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
    @GetMapping("/count")
    public long getTotalOrderCount() {
        return orderService.getTotalOrders();
    }
}