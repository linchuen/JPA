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
@Entity(name = "goods_inventory")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_key", columnNames = "goods_id"),
})
@EntityListeners(AuditingEntityListener.class)
public class GoodsInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal remainAmount;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @OneToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goods;
}
