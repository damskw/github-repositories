package com.recruitment.task.service;

import com.recruitment.task.data.GitHubRepository;
import com.recruitment.task.dto.GitHubRepositoryDto;
import com.recruitment.task.dto.GitHubRepositoryResponse;
import com.recruitment.task.mapper.GitHubRepositoryMapper;
import com.recruitment.task.service.exception.RepositoriesNotFoundException;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.recruitment.task.service.GitHubAPIService.repoAffix;

@ExtendWith(MockitoExtension.class)
class GitHubAPIServiceTest {

    private final String username = "user";
    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private GitHubRepositoryMapper gitHubRepositoryMapper;
    @InjectMocks
    private GitHubAPIService gitHubAPIService;

    private final EasyRandomParameters parameters = new EasyRandomParameters();
    private final EasyRandom easyRandom = new EasyRandom(parameters);

    @Value("${github.api.users.url}")
    private String gitHubApiUrl;


    @Test
    void shouldGetRepositories() {
        //given
        GitHubRepository[] repositories = {easyRandom.nextObject(GitHubRepository.class)};
        repositories[0].setFork(false);
        Mockito.when(restTemplateMock.getForObject(gitHubApiUrl + username + repoAffix, GitHubRepository[].class))
                .thenReturn(repositories);
        //when
        GitHubRepositoryResponse actual = gitHubAPIService.getRepositories(username);
        //then
        Assertions.assertThat(repositories.length).isEqualTo(actual.getRepositories().size());
    }


    @Test
    void shouldThrowNotFoundExceptionWhenGettingHttpClientExceptionNotFound() {
        // given
        Throwable throwable = Assertions.catchThrowable(() -> gitHubAPIService.getRepositories(username));

        // when and then
        Assertions.assertThat(throwable)
                .isInstanceOf(RepositoriesNotFoundException.class);
    }

    @Test
    void shouldThrowRepositoriesNotFoundExceptionWhenNoRepositoriesFound() {
        // given
        List<GitHubRepository> repositories = Instancio.ofList(GitHubRepository.class)
                .size(5)
                .create();
        repositories.stream().peek(r -> r.setFork(true));

        // when and then
        Assertions.assertThatThrownBy(() -> gitHubAPIService.getRepositories(username))
                .isInstanceOf(RepositoriesNotFoundException.class);
    }

    @Test
    void shouldFilterNoForkRepositories() {
        // given
        GitHubRepository gitHubRepository1 = Instancio.of(GitHubRepository.class)
                .set(Select.field(GitHubRepository::getFork), true)
                .create();
        GitHubRepository gitHubRepository2 = Instancio.of(GitHubRepository.class)
                .set(Select.field(GitHubRepository::getFork), true)
                .create();
        GitHubRepository gitHubRepository3 = Instancio.of(GitHubRepository.class)
                .set(Select.field(GitHubRepository::getFork), false)
                .create();

        GitHubRepository[] repositories = {gitHubRepository1, gitHubRepository2, gitHubRepository3};

        // when
        List<GitHubRepositoryDto> actual = gitHubAPIService.filterRepositories(repositories);

        // then
        Assertions.assertThat(actual.size()).isEqualTo(1);
    }
}
