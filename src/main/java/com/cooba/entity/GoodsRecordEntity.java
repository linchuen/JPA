package com.cooba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods_record")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name", columnNames = {"orderId", "goodsId", "transferType"}),
})
public class GoodsRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    private Integer merchantId;
    private Long goodsId;
    private Integer transferType;
    private Integer status;
    @Version
    private Integer version;
    private BigDecimal changeAmount;
    private BigDecimal remainAmount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
