package org.example.Service;

import org.example.Model.Status;
import org.example.Model.Task;

import java.util.List;

public interface ITaskService {
    List<Task> readTasksFromFile();

    void saveTasksToFile();

    void addTask(Task task);

    void deleteTask(Long id);


    Task updateTask(Long id, Task task,Status newStatus);

    List<Task> getAll();

    List<Task> getAllDone();

    List<Task> getAllNotDone();

    List<Task> getAllInProgress();

}


