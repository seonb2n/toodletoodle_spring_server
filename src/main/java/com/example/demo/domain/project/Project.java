package com.example.demo.domain.project;

import com.example.demo.common.util.TokenGenerator;
import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.project.task.Task;
import com.example.demo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project extends BaseEntity {
    private static final String PREFIX_PROJECT = "project_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String projectToken;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String userToken;

    //이름, 마감날짜, 소속 태스크
    private String projectName;
    private String endDayTime;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<Task> taskList = new ArrayList<>();

    @Builder
    public Project(String projectName, String endDayTime, List<Task> taskList, User user, String userToken) {
        this.projectToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_PROJECT);
        this.user = user;
        this.userToken = userToken;
        this.projectName = projectName;
        this.endDayTime = endDayTime;
        this.taskList = new ArrayList<>();
    }

    public void updateProject(ProjectCommand.UpdateProject updateProject) {
        this.projectName = updateProject.getProjectName();
        this.endDayTime = updateProject.getEndDayTime();
    }

    public void changeProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

}
