package com.epam.learning.messageorientedmiddleware.rabbitmq.service;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProduct();

    Product sendProduct(Long id);

    List<Product> sendAll();
}
