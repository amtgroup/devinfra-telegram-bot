package amtgroup.devinfra.telegram.components.gitlab.command.webhook;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.MergeRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
public class GitlabMergeRequestWebhookEvent extends GitlabWebhookEvent {

    @NotNull
    @Valid
    private MergeRequest objectAttributes;

}
