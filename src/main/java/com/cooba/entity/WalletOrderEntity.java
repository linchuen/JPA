package com.cooba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "wallet_order")
@Table(
        uniqueConstraints = @UniqueConstraint(name = "unique_key", columnNames = {"orderId"}),
        indexes = {
                @Index(name = "createdTime", columnList = "createdTime")}
)
public class WalletOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    private Long userId;
    private Integer assetId;
    private Integer transferType;
    private Integer status;
    @Version
    private Integer version;
    private BigDecimal amount;
    private BigDecimal transferBalance;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
