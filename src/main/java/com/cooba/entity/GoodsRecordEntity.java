package com.cooba.entity;

import com.cooba.enums.GoodsTransferEnum;
import com.cooba.enums.converter.GoodsTransferConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    @Convert(converter = GoodsTransferConverter.class)
    private GoodsTransferEnum transferType;
    @Version
    private Integer version;
    private BigDecimal changeAmount;
    private BigDecimal remainAmount;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
