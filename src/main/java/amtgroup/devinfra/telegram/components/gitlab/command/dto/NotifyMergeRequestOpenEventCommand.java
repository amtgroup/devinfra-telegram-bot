package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestWebhookEvent;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class NotifyMergeRequestOpenEventCommand {

    @NotNull
    @Valid
    private GitlabMergeRequestWebhookEvent event;

}
