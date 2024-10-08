package org.example.Controller;

import org.example.Dto.TaskDto;
import org.example.Model.Status;
import org.example.Service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;

@RestController()
@RequestMapping("/api/task")
public class TaskController {


    private ITaskService iTaskService;
    @Autowired
    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody @Validated TaskDto taskDto){
        iTaskService.addTask(taskDto);
        return new ResponseEntity<>("this task add successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        iTaskService.deleteTask(id);
        return new ResponseEntity<>("this task deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<TaskDto> updateTask(@PathVariable Long id , @RequestBody  @Validated TaskDto taskDto, @RequestParam Status newStatus){
      TaskDto updated=  iTaskService.updateTask(id,taskDto,newStatus);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAll(){
         List<TaskDto> taskDtos =iTaskService.getAll();
        return  new ResponseEntity<>(taskDtos,HttpStatus.OK);
    }

    @GetMapping("/allDone")
    public  ResponseEntity<List<TaskDto>> getAllDone(){
        List<TaskDto> taskDtos=iTaskService.getAllDone();
        return new ResponseEntity<>(taskDtos,HttpStatus.OK);
    }

    @GetMapping("/allNotDone")
    public  ResponseEntity<List<TaskDto>> getAllNotDone(){
        List<TaskDto> taskDtos=iTaskService.getAllNotDone();
        return new ResponseEntity<>(taskDtos,HttpStatus.OK);
    }

    @GetMapping("/InProgress")
    public  ResponseEntity<List<TaskDto>> getAllInProgress(){
        List<TaskDto> taskDtos= iTaskService.getAllInProgress();
        return new ResponseEntity<>(taskDtos,HttpStatus.OK);
    }
}
