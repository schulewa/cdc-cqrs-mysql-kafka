package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
public class ShipOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;

}
