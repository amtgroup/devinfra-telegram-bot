package amtgroup.devinfra.telegram.components.gitlab.command.webhook;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Build;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Commit;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.MergeRequestRef;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Pipeline;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Sergey Lukyanets
 */
@Data
public class GitlabPipelineWebhookEvent extends GitlabWebhookEvent {

    @NotNull
    @Valid
    private Pipeline objectAttributes;

    @Valid
    private MergeRequestRef mergeRequest;

    @Valid
    private Commit commit;

    @Valid
    private List<Build> builds;

}
