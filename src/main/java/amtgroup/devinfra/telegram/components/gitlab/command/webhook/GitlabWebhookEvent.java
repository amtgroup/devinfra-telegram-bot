package amtgroup.devinfra.telegram.components.gitlab.command.webhook;

import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Project;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.Repository;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.model.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public abstract class GitlabWebhookEvent {

    @NotBlank
    private String objectKind;
    @NotNull
    @Valid
    private User user;

    @Valid
    private Project project;

    @Valid
    private Repository repository;

}
