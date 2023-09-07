package com.epam.learning.messageorientedmiddleware.rabbitmq.bean;

public class ProductMessagesValidator {

    private ProductMessage productMessageFirst;
    private ProductMessage productMessageSecond;

    public ProductMessagesValidator(ProductMessage productMessageFirst, ProductMessage productMessageSecond) {
        this.productMessageFirst = productMessageFirst;
        this.productMessageSecond = productMessageSecond;
    }

    public ProductMessage getProductMessageFirst() {
        return productMessageFirst;
    }

    public void setProductMessageFirst(ProductMessage productMessageFirst) {
        this.productMessageFirst = productMessageFirst;
    }

    public ProductMessage getProductMessageSecond() {
        return productMessageSecond;
    }

    public void setProductMessageSecond(ProductMessage productMessageSecond) {
        this.productMessageSecond = productMessageSecond;
    }

}
