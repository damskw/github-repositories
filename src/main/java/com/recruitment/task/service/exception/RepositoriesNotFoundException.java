package com.recruitment.task.service.exception;

public class RepositoriesNotFoundException  extends ResourcesNotFoundException {

    public RepositoriesNotFoundException(String username) {
        super("Repositories for user " + username + " not found.");
    }
}
