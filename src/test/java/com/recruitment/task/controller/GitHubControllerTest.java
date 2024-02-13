package com.recruitment.task.controller;

import com.recruitment.task.dto.GitHubRepositoryDto;
import com.recruitment.task.dto.GitHubRepositoryResponse;
import com.recruitment.task.service.GitHubAPIService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@WebFluxTest(GitHubController.class)
class GitHubControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GitHubAPIService gitHubAPIService;

    @Test
    void shouldGetRepositoriesForSpecificUser() {
        // given
        var username = "damskw";
        var repositoryName = "homee-frontend";
        var acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        var expectedResponse = new GitHubRepositoryResponse(List.of(new GitHubRepositoryDto(repositoryName, username, new ArrayList<>())));
        Mockito.when(gitHubAPIService.getRepositories(username)).thenReturn(Mono.just(expectedResponse));

        // when and then
        webTestClient.get()
                .uri("/api/v1/users/repositories?username={username}", username)
                .header("Accept", acceptHeader)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GitHubRepositoryResponse.class)
                .consumeWith(response -> {
                    var actualResponse = response.getResponseBody();
                    Assertions.assertNotNull(actualResponse);
                    var repositories = actualResponse.repositories();
                    Assertions.assertNotNull(repositories);
                    Assertions.assertFalse(repositories.isEmpty());
                    var firstRepo = repositories.get(0);
                    Assertions.assertEquals(repositoryName, firstRepo.name());
                    Assertions.assertEquals(username, firstRepo.ownerLogin());
                });
    }


    @Test
    void shouldReturnBadRequest() {
        // given
        var username = "testUser";
        var expectedStatus = HttpStatus.BAD_REQUEST;

        // when and then
        webTestClient.get()
                .uri("/api/v1/users/repositories?username={username}", username)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    @Test
    void shouldThrowNotAcceptableStatus() {
        // given
        var username = "testUser";
        var expectedStatus = HttpStatus.NOT_ACCEPTABLE;

        // when and then
        webTestClient.get()
                .uri("/api/v1/users/repositories?username={username}", username)
                .header("Accept", "application/yml")
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }


}