package com.epam.learning.messageorientedmiddleware.rabbitmq.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productName")
    private String name;

    @Column(name = "productWeight")
    private int weight;

    @Column(name = "productStatus")
    private String status;

    public Product() {}

    public Product(Long id, String name, int weight, String status) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && name.equals(product.name) && weight == product.weight && status.equals(product.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, status);
    }

}