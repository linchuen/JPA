package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateMerchantRequest {
    @NotBlank
    private String name;
}
