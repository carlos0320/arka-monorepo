package com.arka.cartmcsv.domain.event;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartConfirmEvent {
  private Long cartId;
  private LocalDateTime createdAt;
  private BigDecimal totalPrice;
  private UserData userData;
  private List<ItemData> items;

  @Data
  @NoArgsConstructor
  public static class UserData {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private String address;
    @JsonProperty("client")
    private ClientData client;
  }

  @Data
  @NoArgsConstructor
  public static class ClientData {
    private Long clientId;
    private String customerName;
  }

  @Data
  @NoArgsConstructor
  public static class ItemData {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
  }

  public static CartConfirmEvent fromCart(Cart cart) {
    CartConfirmEvent event = new CartConfirmEvent();
    event.setCartId(cart.getCartId());
    event.setCreatedAt(LocalDateTime.now());
    event.setTotalPrice(cart.getTotalCost());

    if (cart.getUser() != null) {
      UserData userData = new UserData();
      userData.setUserId(cart.getUser().getUserId());
      userData.setName(cart.getUser().getName());
      userData.setPhone(cart.getUser().getPhone());
      userData.setEmail(cart.getUser().getEmail());
      userData.setAddress(cart.getUser().getAddress());

      if (cart.getUser().getClient() != null) {
        ClientData clientData = new ClientData();
        clientData.setClientId(cart.getUser().getClient().getClientId());
        clientData.setCustomerName(cart.getUser().getClient().getCustomerName());
        userData.setClient(clientData);
      }
      event.setUserData(userData);
    }

    if (cart.getItems() != null) {
      event.setItems(cart.getItems().stream()
              .map(item -> {
                ItemData itemData = new ItemData();
                itemData.setProductId(item.getProductId());
                itemData.setQuantity(item.getQuantity());
                itemData.setProductName(item.getProductName());
                itemData.setPrice(item.getUnitPrice());
                return itemData;
              })
              .collect(Collectors.toList()));
    }

    return event;
  }
}
