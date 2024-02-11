package com.recruitment.task.controller.advice;

import com.recruitment.task.service.exception.RepositoriesNotFoundException;
import com.recruitment.task.service.exception.ResourcesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {


    @ExceptionHandler(value = RepositoriesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNoResources(ResourcesNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }


    private record ErrorResponse(
            int status,
            String message
    ){
    }

}
