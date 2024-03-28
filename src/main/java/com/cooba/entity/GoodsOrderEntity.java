package com.cooba.entity;

import com.cooba.enums.GoodsStatusEnum;
import com.cooba.enums.UserTypeEnum;
import com.cooba.enums.converter.GoodsStatusConverter;
import com.cooba.enums.converter.UserTypeConverter;
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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods_order")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_key", columnNames = "orderId"),
})
@EntityListeners(AuditingEntityListener.class)
public class GoodsOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Long userId;
    @Convert(converter = UserTypeConverter.class)
    private UserTypeEnum userType;
    @Convert(converter = GoodsStatusConverter.class)
    private GoodsStatusEnum status;
    @Version
    private Integer version;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
