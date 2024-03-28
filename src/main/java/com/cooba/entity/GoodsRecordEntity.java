package com.cooba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class GoodsRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Integer merchantId;
    private Long goodsId;
    private Integer transferType;
    @Version
    private Integer version;
    private BigDecimal changeAmount;
    private BigDecimal remainAmount;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
