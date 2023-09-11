package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

import com.epam.learning.messageorientedmiddleware.rabbitmq.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMessage {

    private Long id;
    private String name;
    private int weight;

    public ProductMessage(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.weight = product.getWeight();
    }

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
