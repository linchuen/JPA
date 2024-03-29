package com.cooba.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "goods")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name", columnNames = {"merchantId", "name"}),
})
@EntityListeners(AuditingEntityListener.class)
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer merchantId;
    private String name;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "goods", fetch = FetchType.EAGER)
    private GoodsInventoryEntity inventory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goods", fetch = FetchType.EAGER)
    private List<GoodsPriceEntity> price;
}
