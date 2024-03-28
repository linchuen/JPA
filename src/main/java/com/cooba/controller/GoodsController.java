package com.cooba.controller;

import com.cooba.request.CreateGoodsRequest;
import com.cooba.response.BaseResponse;
import com.cooba.response.GoodsResponse;
import com.cooba.response.SuccessResponse;
import com.cooba.result.CreateGoodsResult;
import com.cooba.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/list")
    public BaseResponse<List<GoodsResponse>> list() {
        List<GoodsResponse> goodsResponses = goodsService.listGoods();
        return new SuccessResponse<>(goodsResponses);
    }

    @PostMapping("/create")
    public BaseResponse<CreateGoodsResult> create(@Valid @RequestBody CreateGoodsRequest createGoodsRequest) {
        CreateGoodsResult goodsResult = goodsService.createGoods(createGoodsRequest);
        return new SuccessResponse<>(goodsResult);
    }

}
