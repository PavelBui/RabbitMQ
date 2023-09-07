package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

public class ProductMessage {

    private final Long id;
    private final String name;
    private final String value;

    public ProductMessage(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductMessage product = (ProductMessage) o;
        return id.equals(product.id) && name.equals(product.name) && value.equals(product.value);
    }
}
