package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabIssueCommentWebhookEvent;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class NotifyIssueCommentEventCommand {

    @NotNull
    @Valid
    private GitlabIssueCommentWebhookEvent event;

}
