package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MergeRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String state;
    private String mergeStatus;

    private String description;
    private String url;

    private Boolean workInProgress;

    @NotNull
    private String sourceBranch;
    @NotNull
    private String targetBranch;

    @NotNull
    @Valid
    private ProjectRef source;
    @NotNull
    @Valid
    private ProjectRef target;


    @Valid
    private User assignee;

    @Valid
    @NotNull
    private Commit lastCommit;
}
