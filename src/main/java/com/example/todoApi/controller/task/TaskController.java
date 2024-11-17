package com.example.todoApi.controller.task;

import com.example.todoApi.service.task.TaskEntity;
import com.example.todoApi.service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDTO;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        var entity = taskService.find(taskId);
        var dto = toTaskDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm taskForm) {
        var entity=taskService.create(taskForm.getTitle());
        var dto = toTaskDTO(entity);
        return ResponseEntity.created(URI.create("/tasks/"+dto.getId())).body(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Long limit,Long offset) {

        var dtoList=taskService.selectList(limit,offset)
                .stream()
                .map(this::toTaskDTO).toList();
        var dto = new TaskListDTO();
        var pageDTO = new PageDTO();
        pageDTO.setLimit(limit);
        pageDTO.setOffset(offset);
        pageDTO.setSize(dtoList.size());
        dto.setResults(dtoList);
        dto.setPage(pageDTO);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> editTask(Long taskId,TaskForm taskForm) {

        var dto = toTaskDTO(taskService.update(taskId,taskForm.getTitle()));
        return ResponseEntity.ok(dto);
    }

    private TaskDTO toTaskDTO(TaskEntity task) {
        var dto=new TaskDTO();
        dto.setId(task.id());
        dto.setTitle(task.title());
        return dto;
    }
}
