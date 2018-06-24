package amtgroup.devinfra.telegram.components.notification.command.dto;

import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendNotificationCommand {

    @NotNull
    @Valid
    private ServiceKey serviceKey;

    @NotNull
    @Valid
    private ProjectKey projectKey;

    @NotNull
    @Valid
    private EventTypeId eventTypeId;

    @NotNull
    private JsonNode event;

}
