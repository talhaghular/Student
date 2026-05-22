package com.Project.StudentProject.advice;

import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.exception.DuplicateExceptionResource;
import com.Project.StudentProject.exception.MaxLimitExceptionResource;
import com.Project.StudentProject.exception.NotFoundExceptionResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateExceptionResource.class)
    public ResponceModel handleDuplicateResource(DuplicateExceptionResource ex){
        return new ResponceModel(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(NotFoundExceptionResource.class)
    public ResponceModel handleNotFoundResource(NotFoundExceptionResource ex){
        return new ResponceModel(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(MaxLimitExceptionResource.class)
    public ResponceModel handleMaxLimitExceptionResource(MaxLimitExceptionResource ex){
        return new ResponceModel(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception){
        return exception.getMessage();
//        return new ResponceModel(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "An Unexpected Error Occurs,Please Try Again Later...",
//                null
//        );
    }
//@ExceptionHandler(Exception.class)
//public ResponseEntity<?> handle(Exception ex){
//
//    ex.printStackTrace();
//
//    return ResponseEntity.status(500)
//            .body(ex.getMessage());
//}



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponceModel handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponceModel(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errors
        );
    }
}

