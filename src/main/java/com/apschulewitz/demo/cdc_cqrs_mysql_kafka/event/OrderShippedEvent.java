package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event;

import lombok.Data;

@Data
public class OrderShippedEvent {

    private final String orderId;

}
