package amtgroup.devinfra.telegram.components.bitbucket.command.webhook;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model.PullRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class BitbucketPullRequestWebhookEvent extends BitbucketWebhookEvent {

    @NotNull
    @Valid
    private PullRequest pullRequest;

}
