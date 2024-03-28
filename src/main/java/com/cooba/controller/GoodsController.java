package com.cooba.controller;

import com.cooba.entity.GoodsEntity;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.WalletRequest;
import com.cooba.response.BaseResponse;
import com.cooba.response.SuccessResponse;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.WalletChangeResult;
import com.cooba.service.goods.GoodsService;
import com.cooba.service.user.UserService;
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
    public BaseResponse<List<GoodsEntity>> list(){
        List<GoodsEntity> goodsEntities = goodsService.listGoods();
        return new SuccessResponse<>(goodsEntities);
    }

    @PostMapping("/create")
    public BaseResponse<CreateGoodsResult> create(@Valid @RequestBody CreateGoodsRequest createGoodsRequest){
        CreateGoodsResult goodsResult = goodsService.createGoods(createGoodsRequest);
        return new SuccessResponse<>(goodsResult);
    }

}
