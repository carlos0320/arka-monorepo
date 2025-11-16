package com.arka.cartmcsv.application;

import com.arka.cartmcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;
import com.arka.cartmcsv.domain.model.gateway.UserGateway;
import com.arka.cartmcsv.domain.usecase.cart.*;
import com.arka.cartmcsv.domain.usecase.inventory.GetProductsByIdsUseCase;
import com.arka.cartmcsv.domain.usecase.inventory.ReleaseStockUseCase;
import com.arka.cartmcsv.domain.usecase.inventory.ReserveStockUseCase;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.InventoryGatewayImpl;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.InventoryHttpClient;
import com.arka.cartmcsv.infrastructure.adapter.clients.users.UserGatewayImpl;
import com.arka.cartmcsv.infrastructure.adapter.clients.users.UserHttpClient;
import com.arka.cartmcsv.infrastructure.adapter.repositories.cart.CartEntityRepository;
import com.arka.cartmcsv.infrastructure.adapter.repositories.cart.CartGatewayImpl;
import com.arka.cartmcsv.infrastructure.adapter.repositories.event.OutboxEntityRepository;
import com.arka.cartmcsv.infrastructure.adapter.repositories.event.OutboxEventGatewayImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CartConfig {

  @Value("${user.service.url}")
  private String userServiceUrl;

  @Value("${inventory.service.url}")
  private String inventoryServiceUrl;

  @Bean
  public CartGateway cartGateway(CartEntityRepository cartEntityRepository) {
    return new CartGatewayImpl(cartEntityRepository);
  }

  @Bean
  public InventoryGateway inventoryGateway(InventoryHttpClient inventoryHttpClient) {
    return new InventoryGatewayImpl(inventoryHttpClient);
  }

  @Bean
  public OutboxEventGateway outboxGateway(OutboxEntityRepository outboxEntityRepository) {
    return new OutboxEventGatewayImpl(outboxEntityRepository);
  }

  @Bean
  public UserGateway userGateway(UserHttpClient userHttpClient) {
    return new UserGatewayImpl(userHttpClient);
  }


  @Bean
  public ReserveStockUseCase reserveStockUseCase(InventoryGateway inventoryGateway) {
    return new ReserveStockUseCase(inventoryGateway);
  }

  @Bean
  public ReleaseStockUseCase releaseStockUseCase(InventoryGateway inventoryGateway) {
    return new ReleaseStockUseCase(inventoryGateway);
  }

  @Bean
  public GetProductsByIdsUseCase getProductsByIds(InventoryGateway inventoryGateway) {
    return new GetProductsByIdsUseCase(inventoryGateway);
  }


  @Bean
  public AddCartItemUseCase addCartItemUseCase(
          CartGateway cartGateway,
          ReserveStockUseCase reserveStockUseCase,
          UserGateway userGateway
  ) {
    return new AddCartItemUseCase(cartGateway, reserveStockUseCase,userGateway);
  }

  @Bean
  public UpdateCartItemUseCase updateCartItemUseCase(
          CartGateway cartGateway,
          ReserveStockUseCase reserveStockUseCase,
          ReleaseStockUseCase releaseStockUseCase
  ){
    return new UpdateCartItemUseCase(cartGateway,releaseStockUseCase, reserveStockUseCase);
  }

  @Bean
  public RemoveCartItemUseCase removeCartItemUseCase(CartGateway cartGateway, ReleaseStockUseCase releaseStockUseCase) {
    return new RemoveCartItemUseCase(cartGateway,releaseStockUseCase);
  }

  @Bean
  public ConfirmCartUseCase confirmCartUseCase(CartGateway cartGateway, OutboxEventGateway outboxGateway, ObjectMapper objectMapper) {
    return new ConfirmCartUseCase(cartGateway,outboxGateway, objectMapper);
  }

  @Bean
  public CancelCartUseCase cancelCartUseCase(CartGateway cartGateway, OutboxEventGateway outboxGateway, ObjectMapper objectMapper) {
    return new CancelCartUseCase(cartGateway,outboxGateway, objectMapper);
  }

  @Bean
  public GetUserCartUseCase getUserCartUseCase(CartGateway cartGateway) {
    return new GetUserCartUseCase(cartGateway);
  }

  @Bean
  public GetAbandonedCartsUseCase getAbandonedCartsUseCase(CartGateway cartGateway) {
    return new GetAbandonedCartsUseCase(cartGateway);
  }

  @Bean
  InventoryHttpClient inventoryHttpClient(RestClient.Builder builder){
    RestClient restClient = builder
            .baseUrl(inventoryServiceUrl)
            .build();

    HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();

    return factory.createClient(InventoryHttpClient.class);
  }

  @Bean
  UserHttpClient userHttpClient(RestClient.Builder builder){
    RestClient restClient = builder
            .baseUrl(userServiceUrl)
            .requestInterceptor((request, body, execution) -> {
              ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
              if (attrs != null) {
                String authHeader = attrs.getRequest().getHeader("Authorization");
                if (authHeader != null) {
                  request.getHeaders().add("Authorization", authHeader);
                }
              }
              return execution.execute(request, body);
            })
            .build();

    HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();

    return factory.createClient(UserHttpClient.class);
  }

}
