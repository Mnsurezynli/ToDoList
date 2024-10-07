package org.example.Service;

import org.example.Dto.TaskDto;
import org.example.Model.Status;

import java.util.List;

public interface ITaskService {
    void addTask(TaskDto taskDto);

    void deleteTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto, Status newStatus);

    List<TaskDto> getAll();

    List<TaskDto> getAllNotDone();

    List<TaskDto> getAllInProgress();

    List<TaskDto> getAllDone();

}


