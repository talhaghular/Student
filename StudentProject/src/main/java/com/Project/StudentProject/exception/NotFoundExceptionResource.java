package com.Project.StudentProject.exception;

public class NotFoundExceptionResource extends RuntimeException{
    public NotFoundExceptionResource(String message) {
        super(message);
    }
}
