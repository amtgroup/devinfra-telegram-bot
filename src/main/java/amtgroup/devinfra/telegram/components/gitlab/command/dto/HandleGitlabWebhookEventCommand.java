package amtgroup.devinfra.telegram.components.gitlab.command.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Value
public class HandleGitlabWebhookEventCommand {

    @NotNull
    private JsonNode event;

}
