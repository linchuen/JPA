package com.cooba.util.order;

import com.cooba.enums.OrderEnum;

public interface OrderNumGenerator {

    String generate(OrderEnum orderType);

}
