package com.example.demo.interfaces.project;

import com.example.demo.application.project.ProjectFacade;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.project.Project;
import com.example.demo.interfaces.postIt.PostItDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectApiController {

    private final ProjectFacade projectFacade;
    private final ProjectDtoMapper projectDtoMapper;

    @PostMapping("/register/project")
    public CommonResponse registerProject(@RequestBody ProjectDto.RegisterProjectRequest registerProjectRequest) {
        // 1. registerRequest(dto mapper 로 변환) -> command
        // 2. projectFacade 에서 command 로 등록
        // 3. 결과로 나온 projectInfo -> registerResponse
        // 4. CommonResponse 로 감싸서 return
        var registerCommand = projectDtoMapper.of(registerProjectRequest);
        var registerToken = projectFacade.registerProject(registerCommand);
        return CommonResponse.success(registerToken);
    }

    @PostMapping("/update/project")
    public CommonResponse updateProject(@RequestBody ProjectDto.UpdateProjectRequest updateProjectRequest) {
        var updateCommand = projectDtoMapper.of(updateProjectRequest);
        var updateToken = projectFacade.updateProject(updateCommand);
        return CommonResponse.success(updateToken);
    }

    @PostMapping("/register/task")
    public CommonResponse registerTask(@RequestBody ProjectDto.RegisterTaskRequest registerTaskRequest) {
        var registerCommand = projectDtoMapper.of(registerTaskRequest);
        var registerToken = projectFacade.registerTask(registerCommand);
        return CommonResponse.success(registerToken);
    }

    @PostMapping("/update/task")
    public CommonResponse updateTask(@RequestBody ProjectDto.UpdateTaskRequest updateTaskRequest) {
        var updateCommand = projectDtoMapper.of(updateTaskRequest);
        var updateToken = projectFacade.updateTask(updateCommand);
        return CommonResponse.success(updateToken);
    }

    @PostMapping("/register/action")
    public CommonResponse registerAction(@RequestBody ProjectDto.RegisterActionRequest registerActionRequest) {
        var registerCommand = projectDtoMapper.of(registerActionRequest);
        var registerToken = projectFacade.registerAction(registerCommand);
        return CommonResponse.success(registerToken);
    }

    @PostMapping("/retrieve/project")
    public CommonResponse retrieveProject(@RequestBody ProjectDto.GetProjectWithProjectToken projectWithProjectToken) {
        var retrieveInfo = projectFacade.retrieveProject(projectWithProjectToken.getProjectToken());
        var retrieveResponse = projectDtoMapper.of(retrieveInfo);
        return CommonResponse.success(retrieveResponse);
    }
}
