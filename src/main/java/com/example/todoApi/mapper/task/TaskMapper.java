package com.example.todoApi.mapper.task;

import com.example.todoApi.record.task.TaskRecord;
import com.example.todoApi.service.task.TaskEntity;
import org.apache.ibatis.annotations.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into tasks(title) values (#{title})")
    void insert(TaskRecord taskRecord) ;
    @Select("select id,title from tasks limit #{limit} offset #{offset}")
    List<TaskRecord> selectList(Long limit,Long offset);
    @Select("select * from tasks where id=#{taskId}")
    Optional<TaskRecord> select(long taskId);
    @Update("update tasks set title=#{title} where id=#{id}")
    void update(TaskRecord taskRecord);
    @Delete("delete from tasks where id=#{taskId}")
    void delete(Long taskId);
}
