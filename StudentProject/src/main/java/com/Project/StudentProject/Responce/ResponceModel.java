package com.Project.StudentProject.Responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponceModel {


    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;
}
