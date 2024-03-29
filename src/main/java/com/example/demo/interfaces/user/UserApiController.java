package com.example.demo.interfaces.user;

import com.example.demo.application.user.UserFacade;
import com.example.demo.common.exception.UserLoginFailException;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {
    private final UserFacade userFacade;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/register")
    public CommonResponse registerUser(@RequestBody @Valid UserDto.RegisterRequest registerRequest) {
        //1. 외부에서 전달된 파라미터(dto 형태) -> Command 로 변환
        //2. UserFacade 호출해서 register 처리
        //3. UserFacade 처리 결과(UserInfo) -> UserDto.RegisterResponse 로 변환
        //4. CommonResponse 의 형태로 변환 후 Return

        var userCommand = userDtoMapper.of(registerRequest);
        var userInfo = userFacade.registerUser(userCommand);
        var response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/getuser")
    public CommonResponse getUser(@RequestBody UserDto.GetWithTokenRequest getWithTokenRequest) {
        //1. 파라미터 -> userToken 추출
        //2. UserFacade 호출해서 getUser 처리
        //3. UserFacade 처리 결과(UserInfo) -> UserDto.GetResponse 변환
        //4. CommonResponse 의 형태로 변환 후 Return
        String userToken = getWithTokenRequest.getUserToken();
        var userInfo = userFacade.getUser(userToken);
        var response = new UserDto.GetWithTokenResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody UserDto.LoginRequest loginRequest) {
        try {
            var response = userFacade.login(loginRequest.getUserEmail(), loginRequest.getPassword());
            log.info("user {} 로그인!", loginRequest.getUserEmail());
            return CommonResponse.success(new UserDto.LoginResponse(response));
        } catch (Exception e) {
            log.info("user {} 로그인 실패", loginRequest.getUserEmail(), e);
            throw new UserLoginFailException();
        }
    }
}
