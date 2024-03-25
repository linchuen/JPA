package com.cooba.component.user;

import com.cooba.enums.UserEnum;
import com.cooba.interfaces.Component;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.WalletRequest;
import com.cooba.result.PayResult;
import com.cooba.result.CreateUserResult;
import com.cooba.result.WalletChangeResult;

public interface User extends Component<UserEnum> {

    CreateUserResult create(CreateUserRequest createUserRequest);

    WalletChangeResult deposit( WalletRequest walletRequest);

    WalletChangeResult withdraw(WalletRequest walletRequest);

    PayResult buy(BuyRequest buyRequest);

    UserEnum getEnum();
}
