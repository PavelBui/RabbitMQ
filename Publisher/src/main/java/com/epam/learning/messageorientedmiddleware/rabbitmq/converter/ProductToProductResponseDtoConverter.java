package com.epam.learning.messageorientedmiddleware.rabbitmq.converter;

import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductResponseDto;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductResponseDtoConverter implements Converter<Product, ProductResponseDto> {

    @Override
    public ProductResponseDto convert(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(product, productResponseDto);
        return productResponseDto;
    }
}
