package com.cooba.service.user;

import com.cooba.component.user.User;
import com.cooba.component.user.UserFactory;
import com.cooba.enums.UserEnum;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.UserRepository;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.WalletRequest;
import com.cooba.result.CreateUserResult;
import com.cooba.result.WalletChangeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    @Override
    public WalletChangeResult deposit(WalletRequest walletRequest) {
        userRepository.findById(walletRequest.getUserId()).orElseThrow(UserNotExistException::new);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        return user.deposit(walletRequest);
    }

    @Override
    public WalletChangeResult withdraw(WalletRequest walletRequest) {
        userRepository.findById(walletRequest.getUserId()).orElseThrow(UserNotExistException::new);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        return user.withdraw(walletRequest);
    }

    @Override
    public CreateUserResult create(CreateUserRequest createUserRequest) {
        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        return user.create(createUserRequest);
    }
}
