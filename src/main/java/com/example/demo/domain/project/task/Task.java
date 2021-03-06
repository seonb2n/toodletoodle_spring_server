package com.example.demo.domain.project.task;

import com.example.demo.common.util.TokenGenerator;
import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.project.Project;
import com.example.demo.domain.project.ProjectCommand;
import com.example.demo.domain.project.task.action.Action;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기간 단위로 할 일
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "tasks")
public class Task extends BaseEntity {
    private static final String PREFIX_TASK = "task_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String taskToken;

    //이름, 중요도, 작업기간, 소속 프로젝트, 액션
    private String taskName;

    @Enumerated(value = EnumType.STRING)
    private Importance importance;

    private String startDayTime;
    private String endDayTime;

    @ManyToOne
    @JsonBackReference
    private Project project;
    private String projectToken;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Action> actionList = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Importance {
        HIGH("중요도_높음"), MIDDLE("중요도_중간"),LOW("중요도_낮음");

        private final String description;
    }

    private void changeImportance(String importance) {
        switch (importance) {
            case "HIGH" : this.importance = Importance.HIGH;
                            break;
            case "MIDDLE" : this.importance = Importance.MIDDLE;
                            break;
            case "LOW" : this.importance = Importance.LOW;
                            break;
            default:
                            break;
        }
    }

    @Builder
    public Task(String taskName, String importance, String startDayTime, String endDayTime, Project project, String projectToken, List<Action> actionList) {
        taskToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_TASK);
        this.taskName = taskName;
        this.startDayTime = startDayTime;
        this.endDayTime = endDayTime;
        this.project = project;
        changeImportance(importance);
        this.projectToken = projectToken;
        this.actionList = actionList;
    }

    public void updateTask(Project project, ProjectCommand.UpdateTask updateTask) {
        this.project = project;
        this.projectToken = project.getProjectToken();
        this.taskName = updateTask.getTaskName();
        changeImportance(updateTask.getImportance());
        this.startDayTime = updateTask.getStartDayTime();
        this.endDayTime = updateTask.getEndDayTime();
    }
}
