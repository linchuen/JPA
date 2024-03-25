package com.cooba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name", columnNames = "merchantId,name"),
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
