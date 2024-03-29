package com.cooba.entity;

import com.cooba.enums.WalletStatusEnum;
import com.cooba.enums.WalletTransferEnum;
import com.cooba.enums.converter.GoodsTransferConverter;
import com.cooba.enums.converter.WalletStatusConverter;
import com.cooba.enums.converter.WalletTransferConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
@Entity(name = "wallet_order")
@Table(
        uniqueConstraints = @UniqueConstraint(name = "unique_key", columnNames = {"orderId"}),
        indexes = {
                @Index(name = "createdTime", columnList = "createdTime")}
)
@EntityListeners(AuditingEntityListener.class)
public class WalletOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Long userId;
    private Integer assetId;
    @Convert(converter = WalletTransferConverter.class)
    private WalletTransferEnum transferType;
    @Convert(converter = WalletStatusConverter.class)
    private WalletStatusEnum status;
    @Version
    private Integer version;
    private BigDecimal amount;
    private BigDecimal transferBalance;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
