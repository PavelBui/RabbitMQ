package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMessagesValidator {

    private ProductMessage productMessage;

    private int attemptsNumber;

    public ProductMessagesValidator(ProductMessage productMessage) {
        this.productMessage = productMessage;
        this.attemptsNumber = 0;
    }

    public void increaseAttemptsNumber() {
        attemptsNumber++;
    }

}
