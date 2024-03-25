package com.cooba.controller;

import com.cooba.request.WalletRequest;
import com.cooba.response.BaseResponse;
import com.cooba.response.SuccessResponse;
import com.cooba.result.WalletChangeResult;
import com.cooba.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final UserService userService;

    @PostMapping("/create")
    public BaseResponse<WalletChangeResult> deposit(@Valid @RequestBody WalletRequest walletRequest){
        WalletChangeResult changeResult = userService.deposit(walletRequest);
        return new SuccessResponse<>(changeResult);
    }

}
