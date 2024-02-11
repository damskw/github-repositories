package com.recruitment.task.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GitHubRepositoryDto {

    private String name;
    private String ownerLogin;
    private List<GitHubBranchDto> branches = new ArrayList<>();

    public GitHubRepositoryDto(String name, String ownerLogin) {
        this.name = name;
        this.ownerLogin = ownerLogin;
    }
}
