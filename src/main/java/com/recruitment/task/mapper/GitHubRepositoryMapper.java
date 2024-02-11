package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubBranch;
import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubBranchDto;
import com.recruitment.task.dto.GitHubRepositoryDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.recruitment.task.service.GitHubAPIService.branchAffix;
import static com.recruitment.task.service.GitHubAPIService.branchAffixReplacement;

@Component
public class GitHubRepositoryMapper {

    private final RestTemplate restTemplate;
    private final GitHubBranchMapper gitHubBranchMapper;

    public GitHubRepositoryMapper(RestTemplate restTemplate, GitHubBranchMapper gitHubBranchMapper) {
        this.restTemplate = restTemplate;
        this.gitHubBranchMapper = gitHubBranchMapper;
    }

    public GitHubRepositoryDto mapToRepositoryDto(GitHubRepository gitHubRepository) {
        GitHubRepositoryDto repositoryDto = new GitHubRepositoryDto(
                gitHubRepository.getName(),
                gitHubRepository.getOwner().getLogin()
        );

        String branchesUrl = gitHubRepository.getBranchesUrl().replace(branchAffix, branchAffixReplacement);
        GitHubBranch[] branches = restTemplate.getForObject(branchesUrl, GitHubBranch[].class);

        if (Objects.nonNull(branches)) {
            List<GitHubBranchDto> branchDtos = Arrays.stream(branches)
                    .map(gitHubBranchMapper::mapToBranchDto)
                    .toList();
            repositoryDto.setBranches(branchDtos);
        }

        return repositoryDto;
    }

}
