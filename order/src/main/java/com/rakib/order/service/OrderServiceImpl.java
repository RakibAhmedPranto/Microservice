package com.rakib.order.service;

import com.rakib.order.dto.OrderRequest;
import com.rakib.order.dto.OrderResponse;
import com.rakib.order.model.Order;
import com.rakib.order.model.OrderLineItems;
import com.rakib.order.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItemsDto -> this.modelMapper.map(orderLineItemsDto,OrderLineItems.class)).collect(Collectors.toList());
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderLineItems);
        Order savedOrder = this.orderRepository.save(order);

        return this.modelMapper.map(savedOrder,OrderResponse.class);
    }
}
