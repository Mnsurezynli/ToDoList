package org.example.Service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exception.ResourceNotFoundException;
import org.example.Model.Status;
import org.example.Model.Task;
import org.example.Repository.TaskRepository;
import org.example.Service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    private static final String FILE_PATH = "/home/farazware/Downloads/ToDoList/tasks.json";
    private List<Task> tasks = new ArrayList<>();

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> readTasksFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<Task> tasks = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                this.tasks = mapper.readValue(file, new TypeReference<List<Task>>() {
                });
            } else {
                file.createNewFile();
                saveTasksToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveTasksToFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<Task> tasks = taskRepository.findAll();
        try {
            mapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void addTask(Task task) {
        taskRepository.saveAndFlush(task);
        saveTasksToFile();
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" this task not found with id " + id));
        taskRepository.deleteById(id);
        saveTasksToFile();
    }


    @Transactional
    @Override
    public Task updateTask(Long id, Task task,Status newStatus) {
        task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("this task not found with id " + id));
        task.setUpdatedAt(LocalDateTime.now());
        task.setStatus(newStatus);
        taskRepository.saveAndFlush(task);
        saveTasksToFile();
        return task;
    }


    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();

    }

    @Override
    public List<Task> getAllDone() {
        return taskRepository.findAll()
                .stream().filter(task -> task.getStatus().equals(Status.DONE)).collect(Collectors.toList());

    }

    @Override
    public List<Task> getAllNotDone() {
        return taskRepository.findAll()
                .stream().filter(task -> task.getStatus().equals(Status.NOT_DONE)).collect(Collectors.toList());

    }

    @Override
    public List<Task> getAllInProgress() {
        return taskRepository.findAll()
                .stream().filter(task -> task.getStatus().equals(Status.IN_PROGRESS)).collect(Collectors.toList());

    }

}