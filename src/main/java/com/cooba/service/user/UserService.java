package com.cooba.service.user;

import com.cooba.request.CreateUserRequest;
import com.cooba.request.WalletRequest;
import com.cooba.result.CreateUserResult;
import com.cooba.result.WalletChangeResult;

public interface UserService {
    WalletChangeResult deposit(WalletRequest walletRequest);

    WalletChangeResult withdraw(WalletRequest walletRequest);

    CreateUserResult create(CreateUserRequest createUserRequest);
}
