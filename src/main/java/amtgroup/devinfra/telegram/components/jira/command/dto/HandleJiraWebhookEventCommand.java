package amtgroup.devinfra.telegram.components.jira.command.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class HandleJiraWebhookEventCommand {

    @NotNull
    private JsonNode event;

}
