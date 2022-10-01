package com.rakib.inventory.service;

import com.rakib.inventory.dto.InventoryResponse;
import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
