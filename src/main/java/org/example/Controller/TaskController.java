package org.example.Controller;

import org.example.Model.Status;
import org.example.Model.Task;
import org.example.Service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addTask(@RequestBody @Validated Task task){
        iTaskService.addTask(task);
        return new ResponseEntity<>("this task add successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        iTaskService.deleteTask(id);
        return new ResponseEntity<>("this task deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Task> updateTask(@PathVariable Long id , @RequestBody  @Validated Task task,@RequestParam Status newStatus){
        Task updated=  iTaskService.updateTask(id,task,newStatus);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll(){
        List<Task> tasks =iTaskService.getAll();
        return  new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping("/allDone")
    public  ResponseEntity<List<Task>> getAllDone(){
        List<Task> tasks=iTaskService.getAllDone();
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping("/allNotDone")
    public  ResponseEntity<List<Task>> getAllNotDone(){
        List<Task> tasks=iTaskService.getAllNotDone();
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping("/InProgress")
    public  ResponseEntity<List<Task>> getAllInProgress(){
        List<Task> tasks= iTaskService.getAllInProgress();
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }
}