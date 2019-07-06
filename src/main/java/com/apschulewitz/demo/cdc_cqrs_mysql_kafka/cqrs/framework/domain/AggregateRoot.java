package com.apschulewitz.demo.cdc_cqrs_mysql_kafka.cqrs.framework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AggregateRoot {

    private UUID id;

    protected void setId(UUID id) {
        this.id = id;
    }

}
