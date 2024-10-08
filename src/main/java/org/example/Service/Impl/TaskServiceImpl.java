package org.example.Service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dto.TaskDto;
import org.example.Exception.ResourceNotFoundException;
import org.example.Exception.TaskAlreadyExists;
import org.example.Model.Status;
import org.example.Model.Task;
import org.example.Repository.TaskRepository;
import org.example.Service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    private static final String FILE_PATH = "/home/farazware/Downloads/ToDoList/tasks.json";
    private List<TaskDto> tasks = new ArrayList<>();

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.tasks = getAll();
    }

    private void saveTasksToFile() {
        ObjectMapper mapper = new ObjectMapper();
        List<TaskDto> tasks = getAll();
        try {
            mapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateTaskList() {
        this.tasks = getAll();
    }

    @Transactional
    @Override
    public void addTask(TaskDto taskDto) {
        Task task1 = convertToEntity(taskDto);
        taskRepository.saveAndFlush(task1);
        updateTaskList();
        saveTasksToFile();
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" this task not found with id " + id));
        taskRepository.deleteById(id);
        updateTaskList();
        saveTasksToFile();
    }


    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto, Status newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("this task not found with id " + id));
        task.setUpdatedAt(LocalDateTime.now());
        task.setStatus(newStatus);
        Task task1 = convertToEntity(taskDto);
        taskRepository.saveAndFlush(task1);
        TaskDto taskDto1 = convertToDto(task1);
        updateTaskList();
        saveTasksToFile();
        return taskDto1;
    }


    @Override
    public List<TaskDto> getAll() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<TaskDto> getAllDone() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().filter(task -> task.getStatus().equals(Status.DONE)).map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<TaskDto> getAllNotDone() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().filter(task -> task.getStatus().equals(Status.NOT_DONE)).map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<TaskDto> getAllInProgress() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().filter(task -> task.getStatus().equals(Status.IN_PROGRESS)).map(this::convertToDto).collect(Collectors.toList());

    }


    public TaskDto convertToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setText(task.getText());
        taskDto.setStatus(task.getStatus());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        return taskDto;

    }

    public Task convertToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setText(taskDto.getText());
        task.setStatus(taskDto.getStatus());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setUpdatedAt(taskDto.getUpdatedAt());

        return task;

    }
}