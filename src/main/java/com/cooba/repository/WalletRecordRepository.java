package com.cooba.repository;

import com.cooba.entity.WalletOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRecordRepository extends JpaRepository<WalletOrderEntity, Long> {

}
