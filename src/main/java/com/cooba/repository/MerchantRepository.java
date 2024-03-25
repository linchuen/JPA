package com.cooba.repository;

import com.cooba.entity.MerchantEntity;
import com.cooba.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, Integer> {
}
