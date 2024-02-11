package com.recruitment.task.service.exception;

public abstract class ResourcesNotFoundException extends RuntimeException {

    public ResourcesNotFoundException(String message) {
        super(message);
    }
}
