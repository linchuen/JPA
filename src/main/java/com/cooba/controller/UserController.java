package com.cooba.controller;

import com.cooba.enums.OrderEnum;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.WalletRequest;
import com.cooba.response.BaseResponse;
import com.cooba.response.SuccessResponse;
import com.cooba.result.CreateUserResult;
import com.cooba.result.WalletChangeResult;
import com.cooba.service.user.UserService;
import com.cooba.util.order.OrderNumGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderNumGenerator orderNumGenerator;

    @PostMapping("/deposit")
    public BaseResponse<WalletChangeResult> deposit(@Valid @RequestBody WalletRequest walletRequest) {
        if (walletRequest.getOrderId() == null) {
            String orderId = orderNumGenerator.generate(OrderEnum.WALLET);
            walletRequest.setOrderId(orderId);
        }
        WalletChangeResult changeResult = userService.deposit(walletRequest);
        return new SuccessResponse<>(changeResult);
    }

    @PostMapping("/withdraw")
    public BaseResponse<WalletChangeResult> withdraw(@Valid @RequestBody WalletRequest walletRequest) {
        if (walletRequest.getOrderId() == null) {
            String orderId = orderNumGenerator.generate(OrderEnum.WALLET);
            walletRequest.setOrderId(orderId);
        }
        WalletChangeResult changeResult = userService.withdraw(walletRequest);
        return new SuccessResponse<>(changeResult);
    }

    @PostMapping("/create")
    public BaseResponse<CreateUserResult> create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        CreateUserResult createUserResult = userService.create(createUserRequest);
        return new SuccessResponse<>(createUserResult);
    }
}
