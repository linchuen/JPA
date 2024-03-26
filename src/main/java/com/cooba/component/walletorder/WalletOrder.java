package com.cooba.component.walletorder;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.WalletTransferEnum;
import com.cooba.request.WalletRequest;
import com.cooba.result.WalletChangeResult;

public interface WalletOrder {
    WalletOrderEntity create(WalletRequest walletRequest, WalletTransferEnum transferType);

    void updateStatus(WalletOrderEntity order, WalletChangeResult walletChangeResult);
}
