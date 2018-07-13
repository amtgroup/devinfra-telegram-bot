package amtgroup.devinfra.telegram.components.bitbucket.command.dto;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketRepositoryWebhookEvent;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class NotifyRepositoryRefsChangedCommand {

    @NotNull
    @Valid
    private BitbucketRepositoryWebhookEvent event;

}
