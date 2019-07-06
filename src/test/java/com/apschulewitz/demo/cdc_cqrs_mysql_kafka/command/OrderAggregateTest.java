package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command;

import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.cqrs.framework.domain.OrderAggregate;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderConfirmedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderPlacedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderShippedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void given_no_prior_activity_whenPlaceOrderCommand_then_should_publishOrderPlacedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.givenNoPriorActivity()
                .when(new PlaceOrderCommand(orderId, product))
                .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void givenOrderPlacedEvent_whenShipOrderCommand_then_should_publishOrderShippedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }

}
