package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubBranch;
import com.recruitment.task.dto.GitHubBranchDto;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubBranchMapperTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters();
    private final EasyRandom easyRandom = new EasyRandom(parameters);

    private final GitHubBranchMapper gitHubBranchMapper = new GitHubBranchMapper();


    @Test
    void shouldMapBranchToDto() {
        //given
        GitHubBranch branch = easyRandom.nextObject(GitHubBranch.class);
        //when
        GitHubBranchDto actual = gitHubBranchMapper.mapToBranchDto(branch);
        //then
        GitHubBranchDto expected = new GitHubBranchDto(
                branch.getName(),
                branch.getCommit().getSha()
        );
        Assertions.assertThat(actual).isEqualTo(expected);
    }


}