package com.epam.learning.messageorientedmiddleware.rabbitmq.controller.impl;

import com.epam.learning.messageorientedmiddleware.rabbitmq.controller.ProductController;
import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductRequestDto;
import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductResponseDto;
import com.epam.learning.messageorientedmiddleware.rabbitmq.exception.ProductNotFoundException;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Converter<Product, ProductResponseDto> productToProductResponseDtoConverter;
    @Autowired
    private Converter<ProductRequestDto, Product> productRequestDtoToProductConverter;

    public EntityModel<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.createProduct(productRequestDtoToProductConverter.convert(productRequestDto));
        return toModel(productToProductResponseDtoConverter.convert(product));
    }

    public EntityModel<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.updateProduct(productRequestDtoToProductConverter.convert(productRequestDto));
        return toModel(productToProductResponseDtoConverter.convert(product));
    }

    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    public EntityModel<ProductResponseDto> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id).orElseThrow(() -> new ProductNotFoundException(id));
        return toModel(productToProductResponseDtoConverter.convert(product));
    }

    public List<EntityModel<ProductResponseDto>> getAllProducts() {
        return productService.getAllProduct()
                .stream()
                .map(product -> productToProductResponseDtoConverter.convert(product))
                .map(ProductControllerImpl::toModel)
                .collect(Collectors.toList());
    }

    public EntityModel<ProductResponseDto> sendProduct(@PathVariable Long id) {
        Product product = productService.sendProduct(id);
        return toModel(productToProductResponseDtoConverter.convert(product));
    }

    public List<EntityModel<ProductResponseDto>> sendAll() {
        return productService.sendAll()
                .stream()
                .map(product -> productToProductResponseDtoConverter.convert(product))
                .map(ProductControllerImpl::toModel)
                .collect(Collectors.toList());
    }

    private static EntityModel<ProductResponseDto> toModel(ProductResponseDto response) {
        return EntityModel.of(response,
                linkTo(methodOn(ProductControllerImpl.class).getProduct(response.getId())).withSelfRel(),
                linkTo(methodOn(ProductControllerImpl.class).getAllProducts()).withRel("getAllProducts"),
                linkTo(methodOn(ProductControllerImpl.class).sendProduct(response.getId())).withRel("sendProduct"),
                linkTo(methodOn(ProductControllerImpl.class).sendAll()).withRel("sendAll"),
                linkTo(methodOn(ProductControllerImpl.class).deleteProduct(response.getId())).withRel("deleteProduct"),
                linkTo(methodOn(ProductControllerImpl.class).createProduct(new ProductRequestDto())).withRel("createProduct"),
                linkTo(methodOn(ProductControllerImpl.class).updateProduct(new ProductRequestDto())).withRel("updateProduct")
        );
    }
}
