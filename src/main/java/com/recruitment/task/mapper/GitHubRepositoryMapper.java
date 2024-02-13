package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubBranch;
import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubRepositoryDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.recruitment.task.service.GitHubAPIService.branchAffix;
import static com.recruitment.task.service.GitHubAPIService.branchAffixReplacement;

@Component
public class GitHubRepositoryMapper {

    private final WebClient webClient;
    private final GitHubBranchMapper gitHubBranchMapper;

    public GitHubRepositoryMapper(WebClient webClient, GitHubBranchMapper gitHubBranchMapper) {
        this.webClient = webClient;
        this.gitHubBranchMapper = gitHubBranchMapper;
    }

    public Mono<GitHubRepositoryDto> mapToRepositoryDto(GitHubRepository gitHubRepository) {

        var branchesUrl = gitHubRepository.getBranchesUrl().replace(branchAffix, branchAffixReplacement);
        return webClient.get()
                .uri(branchesUrl)
                .retrieve()
                .bodyToFlux(GitHubBranch.class)
                .map(gitHubBranchMapper::mapToBranchDto)
                .collectList()
                .flatMap(branchList -> Mono.just(new GitHubRepositoryDto(
                        gitHubRepository.getName(),
                        gitHubRepository.getOwner().login(),
                        branchList)));
    }

}
