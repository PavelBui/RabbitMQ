package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMessage {

    private Long id;
    private String name;
    private int weight;

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
