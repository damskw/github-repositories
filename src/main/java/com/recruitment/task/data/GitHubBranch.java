package com.recruitment.task.data;

import com.recruitment.task.dto.GitHubCommitDto;
import lombok.Data;

@Data
public class GitHubBranch {

    private String name;
    private GitHubCommitDto commit;
}
