package amtgroup.devinfra.telegram.components.bitbucket.command.webhook;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model.User;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public abstract class BitbucketWebhookEvent {

    @NotBlank
    private String eventKey;
    @NotNull
    @Valid
    private User actor;

}
