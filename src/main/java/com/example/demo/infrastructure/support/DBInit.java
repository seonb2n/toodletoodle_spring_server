package com.example.demo.infrastructure.support;

import com.example.demo.application.user.UserFacade;
import com.example.demo.domain.postIt.service.PostItStore;
import com.example.demo.domain.project.Project;
import com.example.demo.domain.project.service.ProjectReader;
import com.example.demo.domain.project.service.ProjectStore;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserCommand;
import com.example.demo.domain.user.service.UserReader;
import com.example.demo.domain.user.service.UserStore;
import com.example.demo.infrastructure.postIt.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 초기 테스트 데이터를 넣어주는 class
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class DBInit implements CommandLineRunner {

    private final UserStore userStore;
    private final UserReader userReader;
    private final PostItStore postItStore;
    private final CategoryRepository categoryRepository;
    private final UserFacade userFacade;
    private final ProjectReader projectReader;
    private final ProjectStore projectStore;

    @Override
    public void run(String... args) throws Exception {
        UserCommand.RegisterUser registerUser = UserCommand.RegisterUser.builder()
                .userEmail("test-user@naver.com")
                .userNickName("test-user")
                .startDayTime("20-03-01 09:00")
                .endDayTime("22-06-30 15:00")
                .password("1234")
                .build();
        userFacade.registerUser(registerUser);
        User user = userReader.getUserWithUserEmail("test-user@naver.com");
        user.changeUserToken("user_1234");
        userStore.store(user);

        Project project = Project.builder()
                .projectName("test-project")
                .user(user)
                .userToken("user_1234")
                .projectName("test-project1234")
                .build();
        project.changeProjectToken("project_1234");
        projectStore.store(project);

        Project project2 = Project.builder()
                .projectName("test-project2")
                .user(user)
                .userToken("user_1234")
                .projectName("test-project12345")
                .build();
        project2.changeProjectToken("project_12345");
        projectStore.store(project2);
    }
}
