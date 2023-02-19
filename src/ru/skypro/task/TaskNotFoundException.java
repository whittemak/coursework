package ru.skypro.task;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message){
        super(message);
    }
}
