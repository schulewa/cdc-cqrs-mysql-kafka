package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class OrderPlacedEvent {

    private final String orderId;
    private final String product;

}
