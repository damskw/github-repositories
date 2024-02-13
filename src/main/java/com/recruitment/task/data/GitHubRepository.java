package com.recruitment.task.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GitHubRepository {

    private String name;
    @JsonProperty("branches_url")
    private String branchesUrl;
    private GitHubUser owner;
    private Boolean fork;

    public Boolean isFork() {
        return fork;
    }
}
