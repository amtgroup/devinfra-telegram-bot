package amtgroup.devinfra.telegram.components.gitlab.command.webhook;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Comment;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
public class GitlabSnippetCommentWebhookEvent extends GitlabSnippetWebhookEvent {

    @NotNull
    @Valid
    private Comment objectAttributes;

}
