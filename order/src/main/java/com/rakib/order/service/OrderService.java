package com.rakib.order.service;

import com.rakib.order.dto.OrderRequest;
import com.rakib.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
