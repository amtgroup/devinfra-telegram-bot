package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabSnippetCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class NotifySnippetCommentEventCommand {

    ProjectKey projectKey;

    @NotNull
    @Valid
    private GitlabSnippetCommentWebhookEvent event;

}
