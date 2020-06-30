package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabPipelineWebhookEvent;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class NotifyPipelineFailedEventCommand {

    ProjectKey projectKey;

    @NotNull
    @Valid
    private GitlabPipelineWebhookEvent event;

}
