package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.handler;

import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderConfirmedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderPlacedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.event.OrderShippedEvent;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.query.FindAllOrderedProductsQuery;
import com.apschulewitz.demo.cdc_cqrs_mysql_kafka.query.model.OrderedProduct;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderedProductsEventHandler {

    private final Map<String, OrderedProduct> orderedProducts = new HashMap<>();

    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        orderedProducts.put(orderId, new OrderedProduct(orderId, event.getProduct()));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.setOrderConfirmed();
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
           orderedProduct.setorderShippd();
           return orderedProduct;
        });
    }

    @QueryHandler
    public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orderedProducts.values());
    }
}
