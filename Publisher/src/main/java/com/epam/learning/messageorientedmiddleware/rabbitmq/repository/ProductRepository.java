package com.epam.learning.messageorientedmiddleware.rabbitmq.repository;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
