package amtgroup.devinfra.telegram.components.bitbucket.command.webhook;

import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model.Repository;
import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model.RepositoryChange;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class BitbucketRepositoryWebhookEvent extends BitbucketWebhookEvent {

    @NotNull
    @Valid
    private Repository repository;
    @NotEmpty
    @Valid
    private List<RepositoryChange> changes;

}
