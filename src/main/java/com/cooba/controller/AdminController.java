package com.cooba.controller;

import com.cooba.entity.AdminEntity;
import com.cooba.repository.AdminRepository;
import com.cooba.response.BaseResponse;
import com.cooba.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminRepository adminRepository;

    @PostMapping("/create")
    public BaseResponse<Boolean> create() {
        AdminEntity admin = AdminEntity.builder()
                .id(1L)
                .name("Admin")
                .build();
        adminRepository.save(admin);
        return new SuccessResponse<>(true);
    }
}
