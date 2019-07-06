package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.query.model;

import lombok.Data;

@Data
public class OrderedProduct {

    private final String orderId;
    private final String product;
    private OrderStatus orderStatus;

    public OrderedProduct(String orderId, String product) {
        this.orderId = orderId;
        this.product = product;
        orderStatus = OrderStatus.PLACED;
    }

    public void setOrderConfirmed() {
        orderStatus = OrderStatus.CONFIRMED;
    }

    public void setorderShippd() {
        orderStatus = OrderStatus.SHIPPED;
    }

}

