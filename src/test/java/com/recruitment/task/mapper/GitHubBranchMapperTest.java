package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubBranch;
import com.recruitment.task.dto.GitHubBranchDto;
import com.recruitment.task.dto.GitHubCommitDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GitHubBranchMapperTest {

    @InjectMocks
    private GitHubBranchMapper gitHubBranchMapper;

    @Test
    void testMapToBranchDto() {
        // Given
        GitHubBranch branch = new GitHubBranch("main", new GitHubCommitDto("testSha"));

        // When
        GitHubBranchDto branchDto = gitHubBranchMapper.mapToBranchDto(branch);

        // Then
        assertEquals(branch.name(), branchDto.name());
        assertEquals(branch.commit().sha(), branchDto.lastCommitSHA());
    }
}