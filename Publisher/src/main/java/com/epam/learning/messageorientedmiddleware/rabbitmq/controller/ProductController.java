package com.epam.learning.messageorientedmiddleware.rabbitmq.controller;

import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductRequestDto;
import com.epam.learning.messageorientedmiddleware.rabbitmq.dto.ProductResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
public interface ProductController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EntityModel<ProductResponseDto> createProduct(@RequestBody ProductRequestDto userRequestDto);

    @PutMapping
    EntityModel<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto userRequestDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<String> deleteProduct(@PathVariable Long id);

    @GetMapping("/{id}")
    EntityModel<ProductResponseDto> getProduct(@PathVariable Long id);

    @GetMapping
    List<EntityModel<ProductResponseDto>> getAllProducts();

    @GetMapping("/send/{id}")
    EntityModel<ProductResponseDto> sendProduct(@PathVariable Long id);

    @GetMapping("/send")
    List<EntityModel<ProductResponseDto>> sendAll();

}
