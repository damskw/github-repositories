package com.recruitment.task.service;

import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubRepositoryDto;
import com.recruitment.task.dto.GitHubRepositoryResponse;
import com.recruitment.task.mapper.GitHubRepositoryMapper;
import com.recruitment.task.service.exception.RepositoriesNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GitHubAPIService {

    @Value("${github.api.users.url}")
    private String userRepositoriesUrl;
    public static final String repoAffix = "/repos";
    public static final String branchAffix = "{/branch}";
    public static final String branchAffixReplacement = "";

    private final RestTemplate restTemplate;
    private final GitHubRepositoryMapper gitHubRepositoryMapper;


    public GitHubAPIService(RestTemplate restTemplate, GitHubRepositoryMapper gitHubRepositoryMapper) {
        this.restTemplate = restTemplate;
        this.gitHubRepositoryMapper = gitHubRepositoryMapper;
    }

    public GitHubRepositoryResponse getRepositories(String username) {
        GitHubRepository[] repositories;
        try {
            repositories = restTemplate.getForObject(userRepositoriesUrl + username + repoAffix, GitHubRepository[].class);
        } catch (HttpClientErrorException e) {
            throw (e.getStatusCode() == HttpStatus.NOT_FOUND) ? new RepositoriesNotFoundException(username) : new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(repositories)) {
            throw new RepositoriesNotFoundException(username);
        }

        List<GitHubRepositoryDto> repositoryDtos = filterRepositories(repositories);

        return new GitHubRepositoryResponse(repositoryDtos);
    }

    public List<GitHubRepositoryDto> filterRepositories(GitHubRepository[] repositories) {
        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(gitHubRepositoryMapper::mapToRepositoryDto)
                .toList();
    }

}
