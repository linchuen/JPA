package com.cooba.component.user;

import com.cooba.component.FactoryTemplate;
import com.cooba.enums.UserEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFactory extends FactoryTemplate<UserEnum, User> {

    public UserFactory(List<User> users) {
        super(users);
    }

    public User getByType(int type){
        UserEnum userEnum = UserEnum.getEnum(type).orElseThrow();
        return getByEnum(userEnum);
    }
}
