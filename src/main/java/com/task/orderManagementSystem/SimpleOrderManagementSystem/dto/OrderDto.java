package com.task.orderManagementSystem.SimpleOrderManagementSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private long orderId;
    private double orderAmount;
}
