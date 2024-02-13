package com.recruitment.task.data;

import com.recruitment.task.dto.GitHubCommitDto;


public record GitHubBranch(String name, GitHubCommitDto commit) {
}