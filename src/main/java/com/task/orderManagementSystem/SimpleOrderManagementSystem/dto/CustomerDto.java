package com.task.orderManagementSystem.SimpleOrderManagementSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private long customerId;
    private String customerName;
    private String customerAddress;
    private String customerContact;
}
