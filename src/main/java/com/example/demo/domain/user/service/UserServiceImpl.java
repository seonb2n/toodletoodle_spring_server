package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserCommand;
import com.example.demo.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    @Transactional
    public UserInfo.Main registerUser(UserCommand.RegisterUser registerUserCommand) {
        // 1.command -> initUser
        // 2. save initUser to DB
        // 3. User -> UserInfo and return
        var initUser = registerUserCommand.toEntity();
        User user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }


}