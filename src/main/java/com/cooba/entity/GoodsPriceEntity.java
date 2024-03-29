package com.cooba.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods_price")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_key", columnNames = {"goods_id", "assetId"}),
})
@EntityListeners(AuditingEntityListener.class)
public class GoodsPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer assetId;
    private BigDecimal price;
    @Version
    private Integer version;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goods;
}
