package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MergeRequestRef {

    @NotBlank
    private String id;

    @NotBlank
    private String iid;
    @NotBlank
    private String title;
    @NotBlank
    private String state;
    private String mergeStatus;

    private String url;

    @NotNull
    private String sourceBranch;
    @NotNull
    private String targetBranch;

    @NotNull
    private String sourceProjectId;
    @NotNull
    private String targetProjectId;
}
