package com.cooba.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMerchantResult {
    private Integer id;
    private String name;
}
