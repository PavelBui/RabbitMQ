package com.epam.learning.messageorientedmiddleware.rabbitmq.converter;

import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductRequestDto;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProductRequestDtoToProductConverter implements Converter<ProductRequestDto, Product> {

    @Override
    public Product convert(ProductRequestDto productRequestDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequestDto, product);
        product.setWeight(Integer.parseInt(productRequestDto.getWeight()));
        return product;
    }
}
