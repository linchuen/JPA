package com.cooba.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResult {
    private Long id;
    private String name;
}
