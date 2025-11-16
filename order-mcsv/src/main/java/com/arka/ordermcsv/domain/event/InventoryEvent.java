package com.arka.ordermcsv.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {
  private Long orderId;
  private List<InventoryItem> items;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class InventoryItem {
    private Long productId;
    private Integer quantity;
  }

  public static InventoryEvent fromOrderEvent(OrderEvent orderEvent) {
    List<InventoryItem> items = orderEvent.getItems().stream()
            .map(i -> new InventoryItem(i.getProductId(), i.getQuantity()))
            .toList();
    return new InventoryEvent(orderEvent.getOrderId(), items);
  }
}
