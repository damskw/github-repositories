package com.recruitment.task.mapper;

import com.recruitment.task.data.GitHubBranch;
import com.recruitment.task.dto.GitHubBranchDto;
import org.springframework.stereotype.Component;

@Component
public class GitHubBranchMapper {


    public GitHubBranchDto mapToBranchDto(GitHubBranch branch) {
        GitHubBranchDto branchDto = new GitHubBranchDto();
        branchDto.setName(branch.getName());
        branchDto.setLastCommitSHA(branch.getCommit().getSha());
        return branchDto;
    }

}
