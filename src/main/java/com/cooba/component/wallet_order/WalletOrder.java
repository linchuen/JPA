package com.cooba.component.wallet_order;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.TransferTypeEnum;
import com.cooba.request.WalletRequest;
import com.cooba.result.WalletChangeResult;

public interface WalletOrder {
    WalletOrderEntity create(WalletRequest walletRequest, TransferTypeEnum transferType);

    void updateStatus(WalletOrderEntity order, WalletChangeResult walletChangeResult);
}
