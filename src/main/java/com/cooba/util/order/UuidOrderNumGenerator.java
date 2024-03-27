package com.cooba.util.order;

import com.cooba.enums.OrderEnum;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidOrderNumGenerator implements OrderNumGenerator {
    @Override
    public String generate(OrderEnum orderType) {
        return orderType.name() + ":" + UUID.randomUUID();
    }
}
