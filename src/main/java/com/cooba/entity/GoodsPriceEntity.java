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
@Entity(name = "goods_price")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_key", columnNames = {"goodsId", "assetId"}),
})
public class GoodsPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer merchantId;
    private Long goodsId;
    private Integer assetId;
    private BigDecimal price;
    @Version
    private Integer version;
    private LocalDateTime updatedTime;

}
