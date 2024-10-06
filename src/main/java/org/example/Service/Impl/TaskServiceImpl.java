package org.example.Service.Impl;

import javassist.NotFoundException;
import org.example.Dto.TaskDto;
import org.example.Exception.ResourceNotFoundException;
import org.example.Exception.TaskAlreadyExists;
import org.example.Model.Task;
import org.example.Repository.TaskRepository;
import org.example.Service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Transactional
    public void addTask(TaskDto taskDto) {
        Optional<Task> task= taskRepository.findById();
        if (task.isPresent()){
            throw new TaskAlreadyExists("this task already exists");
        }
        Task task1=convertToEntity(taskDto);
        taskRepository.saveAndFlush(task1);
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        taskRepository.deleteById(id);
    }

    public TaskDto updateTask(Long id) {

    }

    public List<TaskDto> getAll() {

    }

    public List<TaskDto> getAllDone(){

    }

    public List<TaskDto> getAllNotDone() {

    }

    public List<TaskDto> getAllInProgress() {

    }


    public TaskDto convertToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setText(task.getText());
        taskDto.setStatus(task.getStatus());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        return taskDto;
    }

    public Task convertToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setText(taskDto.getText());
        task.setStatus(taskDto.getStatus());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setUpdatedAt(taskDto.getUpdatedAt());
        return task;
    }
}
