package com.cooba.request;

import lombok.Data;

@Data
public class CreateGoodsRequest {
    private Integer merchantId;
    private String name;
}
