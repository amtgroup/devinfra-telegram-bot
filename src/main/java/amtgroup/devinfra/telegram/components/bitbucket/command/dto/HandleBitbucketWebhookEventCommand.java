package amtgroup.devinfra.telegram.components.bitbucket.command.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class HandleBitbucketWebhookEventCommand {

    @NotNull
    private JsonNode event;

}
