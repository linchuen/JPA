package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class RestockResult {
   private List<InventoryChangeResult> changeResults;
}
