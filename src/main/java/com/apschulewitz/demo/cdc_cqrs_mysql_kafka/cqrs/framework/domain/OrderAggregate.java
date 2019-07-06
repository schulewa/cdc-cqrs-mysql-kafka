package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.cqrs.framework.domain;

import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.ConfirmOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.PlaceOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.ShipOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderConfirmedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderPlacedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderShippedEvent;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand command) {
        apply(new OrderPlacedEvent(command.getOrderId(), command.getProduct()));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new IllegalStateException("Cannot ship an order which has not been confirmed yet.");
        }

        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

    protected OrderAggregate() {
        // Required by Axon to build a default Aggregate prior to Event Sourcing
    }

}
