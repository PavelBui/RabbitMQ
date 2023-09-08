package com.epam.learning.messageorientedmiddleware.rabbitmq.service.impl;

import com.epam.learning.messageorientedmiddleware.rabbitmq.exception.ProductNotFoundException;
import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import com.epam.learning.messageorientedmiddleware.rabbitmq.repository.ProductRepository;
import com.epam.learning.messageorientedmiddleware.rabbitmq.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PublishService publishService;

    @Override
    public Product createProduct(Product product) {
        Long id = product.getId();
        return productRepository.findById(id)
                .orElseGet(() -> {
                    product.setId(null);
                    return productRepository.save(product);
                });
    }

    @Override
    public Product updateProduct(Product product) {
        Long id = product.getId() != null ? product.getId() : 0L;
        return productRepository.findById(id)
                .map(currentProduct -> {
                    currentProduct.setName(product.getName());
                    currentProduct.setWeight(product.getWeight());
                    currentProduct.setStatus(product.getStatus());
                    return productRepository.save(currentProduct);
                })
                .orElseGet(() -> productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(product.getId());
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product sendProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        if (!product.getStatus().equals("Done")) {
            publishService.send(product);
            product.setStatus("Done");
            updateProduct(product);
        }
        return product;
    }

    @Override
    public List<Product> sendAll() {
        return ((List<Product>) productRepository.findAll()).stream()
                .filter(product -> !product.getStatus().equals("Done"))
                .peek(product -> {
                    publishService.send(product);
                    product.setStatus("Done");
                    updateProduct(product);
                })
                .collect(Collectors.toList());
    }
}
