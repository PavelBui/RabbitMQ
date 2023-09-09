package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

public class ProductMessagesValidator {

    private ProductMessage productMessage;

    private int attemptsNumber;

    public ProductMessagesValidator(ProductMessage productMessage) {
        this.productMessage = productMessage;
        this.attemptsNumber = 0;
    }

    public ProductMessage getProductMessage() {
        return productMessage;
    }

    public void setProductMessage(ProductMessage productMessage) {
        this.productMessage = productMessage;
    }

    public int getAttemptsNumber() {
        return attemptsNumber;
    }

    public int increaseAttemptsNumber() {
        return attemptsNumber++;
    }

}
