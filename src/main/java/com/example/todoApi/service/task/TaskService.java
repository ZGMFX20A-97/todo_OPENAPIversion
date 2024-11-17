package com.example.todoApi.service.task;

import com.example.todoApi.mapper.task.TaskMapper;
import com.example.todoApi.record.task.TaskRecord;
import com.example.todoapi.model.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;

    public TaskEntity find(long taskId) {

        return taskMapper.select(taskId)
                .map(record->new TaskEntity(record.id(), record.title()))
                .orElseThrow(()->new TaskEntityNotFoundException(taskId));
    }

    public List<TaskEntity> selectList(Long limit,Long offset){

        return taskMapper.selectList(limit,offset).stream()
                .map(record->new TaskEntity(record.id(), record.title())).toList();
    }
    public TaskEntity create(String title) {
        var taskRecord = new TaskRecord(null, title);
        taskMapper.insert(taskRecord);
        return new TaskEntity(taskRecord.id(), taskRecord.title());
    }

    public TaskEntity update(Long taskId, @NotNull @Size(min = 1, max = 256) String title) {
        taskMapper.select(taskId).orElseThrow(()->new TaskEntityNotFoundException(taskId));
        taskMapper.update(new TaskRecord(taskId,title));
        return find(taskId);
    }

    public void delete(Long taskId) {
        taskMapper.select(taskId).orElseThrow(()->new TaskEntityNotFoundException(taskId));
        taskMapper.delete(taskId);
    }
}
