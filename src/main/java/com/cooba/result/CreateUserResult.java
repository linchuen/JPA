package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CreateUserResult {
    private Long id;
    private String name;
}
