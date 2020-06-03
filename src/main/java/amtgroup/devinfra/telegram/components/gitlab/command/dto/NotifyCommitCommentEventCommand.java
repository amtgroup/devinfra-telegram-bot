package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabCommitCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class NotifyCommitCommentEventCommand {

    ProjectKey projectKey;

    @NotNull
    @Valid
    private GitlabCommitCommentWebhookEvent event;

}
