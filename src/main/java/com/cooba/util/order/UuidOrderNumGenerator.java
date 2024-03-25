package com.cooba.util.order;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidOrderNumGenerator implements OrderNumGenerator{
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
