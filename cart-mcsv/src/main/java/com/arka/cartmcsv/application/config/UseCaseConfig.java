package com.arka.cartmcsv.application.config;

import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.model.usecase.AddCartItemUseCase;
import com.arka.cartmcsv.domain.model.usecase.GetCartItemsUseCase;
import com.arka.cartmcsv.domain.model.usecase.UpdateCartItemUseCase;
import com.arka.cartmcsv.infrastructure.adapters.mappers.CartItemMapper;
import com.arka.cartmcsv.infrastructure.adapters.mappers.CartMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackages = {
                "com.arka.cartmcsv",
          "com.arka.cartmcsv.domain.usecase",
          "com.arka.cleanarchitecture.infrastructure.adapters.repository",
          "com.arka.cleanarchitecture.infrastructure.adapters.mapper",
          "com.arka.cleanarchitecture.infrastructure.controller"
        },
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "^.+UseCase$"
                )
        },
        useDefaultFilters = false
)
public class UseCaseConfig {

}
