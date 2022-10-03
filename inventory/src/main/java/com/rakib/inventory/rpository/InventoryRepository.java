package com.rakib.inventory.rpository;

import com.rakib.inventory.dto.InventoryResponse;
import com.rakib.inventory.model.Inventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);

    @Query("select new com.rakib.inventory.dto.InventoryResponse(i.skuCode, i.quantity, CASE WHEN (i.quantity > 0) THEN true else false end) " +
        "from Inventory i where i.skuCode in :skuCode")
    List<InventoryResponse> findInventoryResponse(@Param("skuCode") List<String> skuCode);
}
