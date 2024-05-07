package com.usersevice.user.Dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {



    private Long userId;
    private String paymentMethod;
    private List<OrderItemRequest> orderItems;

}

