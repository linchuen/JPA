package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateGoodsRequest {
    @NotNull
    private Integer merchantId;
    @NotBlank
    private String name;
}
