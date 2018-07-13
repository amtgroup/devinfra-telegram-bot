package amtgroup.devinfra.telegram.components.jira.command.dto;

import amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class SendJiraIssueUpdatedEventCommand {

    @NotNull
    @Valid
    private JiraIssueWebhookEvent event;

}
