package amtgroup.devinfra.telegram.components.bitbucket.command.dto;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestCommentWebhookEvent;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class NotifyPullRequestCommentDeletedEventCommand {

    @NotNull
    @Valid
    private BitbucketPullRequestCommentWebhookEvent event;

}