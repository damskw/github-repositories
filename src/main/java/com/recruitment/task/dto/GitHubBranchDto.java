package com.recruitment.task.dto;

import lombok.Data;

@Data
public class GitHubBranchDto {

    private String name;
    private String lastCommitSHA;

    public GitHubBranchDto(String name, String lastCommitSHA) {
        this.name = name;
        this.lastCommitSHA = lastCommitSHA;
    }


    public GitHubBranchDto() {
    }
}
