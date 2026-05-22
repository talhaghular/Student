package com.Project.StudentProject.exception;

public class MaxLimitExceptionResource extends RuntimeException{
    public MaxLimitExceptionResource(String message) {
        super(message);
    }
}
