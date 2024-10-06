package org.example.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class GlobalHandlerException {

    @ExceptionHandler(TaskAlreadyExists.class)
    public ResponseEntity<String> handleTaskAlreadyExists(TaskAlreadyExists ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

   @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
   }
}
