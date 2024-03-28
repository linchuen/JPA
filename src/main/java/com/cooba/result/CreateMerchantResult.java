package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CreateMerchantResult {
    private Integer id;
    private String name;
}
