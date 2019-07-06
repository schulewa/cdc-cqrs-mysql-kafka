package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event;

import lombok.Data;

@Data
public class OrderConfirmedEvent {

    private final String orderId;

}
