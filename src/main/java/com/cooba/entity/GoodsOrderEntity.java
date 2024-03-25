package com.cooba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods_order")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_key", columnNames = "orderId"),
})
public class GoodsOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    private List<Long> recordIds;
    private Integer status;
    @Version
    private Integer version;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
