package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.controller;

import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.ConfirmOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.PlaceOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.command.ShipOrderCommand;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.query.FindAllOrderedProductsQuery;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.query.model.OrderedProduct;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/ship-order")
    public void shipOrder() {
        String orderId = UUID.randomUUID().toString();
        commandGateway.send(new PlaceOrderCommand(orderId, "Deluxe Chair"));
        commandGateway.send(new ConfirmOrderCommand(orderId));
        commandGateway.send(new ShipOrderCommand(orderId));
    }

    @GetMapping("all-mapping")
    public List<OrderedProduct> findAllOrderedProductsQuery() {
        return queryGateway.query(new FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf(OrderedProduct.class)).join();
    }
}
