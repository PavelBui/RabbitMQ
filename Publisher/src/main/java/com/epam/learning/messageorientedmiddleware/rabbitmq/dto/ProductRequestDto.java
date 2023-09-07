package com.epam.learning.messageorientedmiddleware.rabbitmq.dto;

public class ProductRequestDto {

    private Long id;

    private String name;

    private String value;

    private String status;

    public ProductRequestDto() {}

    public ProductRequestDto(Long id, String name, String value, String status) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
