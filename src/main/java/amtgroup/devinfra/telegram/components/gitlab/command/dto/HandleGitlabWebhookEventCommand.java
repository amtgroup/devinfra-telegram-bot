package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class HandleGitlabWebhookEventCommand {

    ProjectKey projectKey;

    @NotNull
    private JsonNode event;


}
