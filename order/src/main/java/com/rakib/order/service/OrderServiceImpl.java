package com.rakib.order.service;

import com.rakib.order.dto.InventoryResponse;
import com.rakib.order.dto.OrderRequest;
import com.rakib.order.dto.OrderResponse;
import com.rakib.order.model.Order;
import com.rakib.order.model.OrderLineItems;
import com.rakib.order.repository.OrderRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItemsDto -> this.modelMapper.map(orderLineItemsDto,OrderLineItems.class)).collect(Collectors.toList());
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderItems -> orderItems.getSkuCode())
            .toList();

//        System.out.println(skuCodes.toString());

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
            .uri("http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

        boolean allMatch = Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allMatch){
            Order savedOrder = this.orderRepository.save(order);
            return this.modelMapper.map(savedOrder,OrderResponse.class);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
}
