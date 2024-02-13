package com.recruitment.task.dto;

import java.util.List;

public record GitHubRepositoryResponse(List<GitHubRepositoryDto> repositories) {
}