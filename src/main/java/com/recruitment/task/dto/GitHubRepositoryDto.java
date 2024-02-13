package com.recruitment.task.dto;

import java.util.List;

public record GitHubRepositoryDto(String name, String ownerLogin, List<GitHubBranchDto> branches) {
}
