package com.epam.learning.messageorientedmiddleware.rabbitmq.dto;

public class ProductRequestDto {

    private Long id;

    private String name;

    private String weight;

    private String status;

    public ProductRequestDto() {}

    public ProductRequestDto(Long id, String name, String weight, String status) {
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
