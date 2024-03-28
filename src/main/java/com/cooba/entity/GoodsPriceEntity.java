package com.cooba.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
        @UniqueConstraint(name = "unique_key", columnNames = {"goods_id", "assetId"}),
})
@EntityListeners(AuditingEntityListener.class)
public class GoodsPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
