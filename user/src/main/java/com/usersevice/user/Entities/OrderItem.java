package com.usersevice.user.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference 
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private int quantity;

}
