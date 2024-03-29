package com.recruitment.task.controller;

import com.recruitment.task.dto.GitHubRepositoryResponse;
import com.recruitment.task.service.GitHubAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v1/users/")
public class GitHubController {

    private final GitHubAPIService gitHubAPIService;

    public GitHubController(GitHubAPIService gitHubAPIService) {
        this.gitHubAPIService = gitHubAPIService;
    }

    @GetMapping(path = "repositories", produces = MediaType.APPLICATION_JSON_VALUE)
    public GitHubRepositoryResponse getRepositories(@RequestParam String username, @RequestHeader("Accept") String acceptHeader) {
        if (!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accept header must be application/json");
        }
        return gitHubAPIService.getRepositories(username).block();
    }
}


