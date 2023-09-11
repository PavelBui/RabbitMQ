package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductMessage {

    private final Long id;
    private final String name;
    private final int weight;

    @Override
    public String toString() {
        return '{' +
               "id=" + id +
               ", name=" + name +
               ", weight=" + weight +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductMessage product = (ProductMessage) o;
        return id.equals(product.id) && name.equals(product.name) && weight == product.weight;
    }
}
