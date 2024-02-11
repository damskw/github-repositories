package com.recruitment.task.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GitHubRepositoryResponse {

    private List<GitHubRepositoryDto> repositories = new ArrayList<>();

    public GitHubRepositoryResponse(List<GitHubRepositoryDto> repositories) {
        this.repositories = repositories;
    }

    public GitHubRepositoryResponse() {

    }
}
