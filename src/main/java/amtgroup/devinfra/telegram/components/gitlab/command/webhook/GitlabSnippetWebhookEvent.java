package amtgroup.devinfra.telegram.components.gitlab.command.webhook;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Snippet;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
public class GitlabSnippetWebhookEvent extends GitlabWebhookEvent {

    @NotNull
    @Valid
    private Snippet snippet;

}
