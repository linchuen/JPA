package com.cooba.repository;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.WalletStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletOrderRepository extends JpaRepository<WalletOrderEntity, Long> {
    @Modifying
    @Query("UPDATE wallet_order " +
            "SET transfer_balance=?2, status=?3, updated_time=NOW(), version=version+1" +
            "WHERE id=?1")
    void updateStatusById(Long id, BigDecimal transferBalance, WalletStatusEnum status);

}
