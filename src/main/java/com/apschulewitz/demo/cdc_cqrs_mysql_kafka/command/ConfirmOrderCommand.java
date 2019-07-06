package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ConfirmOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;


}
