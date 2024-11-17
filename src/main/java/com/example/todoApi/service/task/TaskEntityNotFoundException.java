package com.example.todoApi.service.task;

public class TaskEntityNotFoundException extends RuntimeException{
    private long taskId;
    public TaskEntityNotFoundException(long taskId){
        super ("TaskEntity(id=" + taskId + ") not found");
        this.taskId=taskId;
    }

}
