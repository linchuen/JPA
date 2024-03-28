package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestockResult {
   private final List<InventoryChangeResult> changeResults;
}
