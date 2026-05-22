package com.Project.StudentProject.exception;

public class DuplicateExceptionResource extends RuntimeException{

    public DuplicateExceptionResource(String message) {
        super(message);
    }
}
