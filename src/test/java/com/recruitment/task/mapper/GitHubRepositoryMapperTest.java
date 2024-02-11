package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubRepositoryDto;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class GitHubRepositoryMapperTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GitHubBranchMapper gitHubBranchMapper = new GitHubBranchMapper();

    private final GitHubRepositoryMapper gitHubRepositoryMapper = new GitHubRepositoryMapper(restTemplate, gitHubBranchMapper);
    private final EasyRandomParameters parameters = new EasyRandomParameters();
    private final EasyRandom easyRandom = new EasyRandom(parameters);

    @Test
    void shouldMapRepositoryToDto() {
        //given
        GitHubRepository repo = easyRandom.nextObject(GitHubRepository.class);
        repo.setBranchesUrl("https://api.github.com/users/testuser/repos{/branch}");
        //when
        GitHubRepositoryDto actual = gitHubRepositoryMapper.mapToRepositoryDto(repo);
        //then
        GitHubRepositoryDto expected = new GitHubRepositoryDto(
                repo.getName(),
                repo.getOwner().getLogin()
        );
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}