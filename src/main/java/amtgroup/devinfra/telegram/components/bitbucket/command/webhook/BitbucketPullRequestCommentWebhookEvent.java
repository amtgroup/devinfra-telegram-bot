package amtgroup.devinfra.telegram.components.bitbucket.command.webhook;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model.Comment;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class BitbucketPullRequestCommentWebhookEvent extends BitbucketPullRequestWebhookEvent {

    @NotNull
    @Valid
    private Comment comment;

}
