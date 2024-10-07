package org.example.Service;

import org.example.Dto.TaskDto;

import java.util.List;

public interface ITaskService {
    void addTask(TaskDto taskDto);

    void deleteTask(Long id);

    TaskDto updateTask(Long id,TaskDto taskDto);

    List<TaskDto> getAll();

    List<TaskDto> getAllNotDone();

    List<TaskDto> getAllInProgress();

    List<TaskDto> getAllDone();

}


