package com.cooba.controller;

import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.response.BaseResponse;
import com.cooba.response.SuccessResponse;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;
import com.cooba.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/create")
    public BaseResponse<CreateMerchantResult> create(@Valid @RequestBody CreateMerchantRequest createMerchantRequest) {
        CreateMerchantResult createMerchantResult = shopService.createMerchant(createMerchantRequest);
        return new SuccessResponse<>(createMerchantResult);
    }

    @PostMapping("/goods/restock")
    public BaseResponse<RestockResult> restockGoods(@Valid @RequestBody RestockRequest restockRequest) {
        RestockResult restockResult = shopService.restockGoods(restockRequest);
        return new SuccessResponse<>(restockResult);
    }

    @PostMapping("/goods/sale")
    public BaseResponse<PayResult> saleGoods(@Valid @RequestBody BuyRequest buyRequest) {
        PayResult payResult = shopService.saleGoods(buyRequest);
        return new SuccessResponse<>(payResult);
    }
}
