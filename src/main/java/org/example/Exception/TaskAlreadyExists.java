package org.example.Exception;

public class TaskAlreadyExists extends RuntimeException {

    public TaskAlreadyExists(String message) {
        super(message);
    }
}
