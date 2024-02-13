package com.recruitment.task.service;

import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubRepositoryResponse;
import com.recruitment.task.mapper.GitHubRepositoryMapper;
import com.recruitment.task.service.exception.RepositoriesNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GitHubAPIService {

    @Value("${github.api.users.url}")
    private String userRepositoriesUrl;
    public static final String repoAffix = "/repos";
    public static final String branchAffix = "{/branch}";
    public static final String branchAffixReplacement = "";

    private final WebClient webClient;
    private final GitHubRepositoryMapper gitHubRepositoryMapper;

    public GitHubAPIService(WebClient.Builder webClientBuilder, GitHubRepositoryMapper gitHubRepositoryMapper) {
        this.webClient = webClientBuilder.baseUrl(userRepositoriesUrl).build();
        this.gitHubRepositoryMapper = gitHubRepositoryMapper;
    }

    public Mono<GitHubRepositoryResponse> getRepositories(String username) {
        return webClient.get()
                .uri(userRepositoriesUrl + username + repoAffix)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RepositoriesNotFoundException(username)))
                .bodyToFlux(GitHubRepository.class)
                .filter(repo -> !repo.isFork())
                .flatMap(gitHubRepositoryMapper::mapToRepositoryDto)
                .collectList()
                .map(GitHubRepositoryResponse::new);
    }

}
