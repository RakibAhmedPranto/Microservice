package com.rakib.inventory.service;

import com.rakib.inventory.dto.InventoryResponse;
import com.rakib.inventory.model.Inventory;
import com.rakib.inventory.rpository.InventoryRepository;
import java.util.List;
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
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        List<Inventory> inventoryList = this.inventoryRepository.findBySkuCodeIn(skuCode);

        return inventoryList.stream().map(inventoryResponse -> {
            if(inventoryResponse.getQuantity() > 0){
                return InventoryResponse.builder().skuCode(inventoryResponse.getSkuCode()).isInStock(true).quantity(inventoryResponse.getQuantity()).build();
            }
            return InventoryResponse.builder().skuCode(inventoryResponse.getSkuCode()).isInStock(false).quantity(inventoryResponse.getQuantity()).build();
        }).collect(
            Collectors.toList());
    }
}
