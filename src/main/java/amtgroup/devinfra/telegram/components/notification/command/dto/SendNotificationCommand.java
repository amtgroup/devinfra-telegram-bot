package amtgroup.devinfra.telegram.components.notification.command.dto;

import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

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

    @NotEmpty
    @Valid
    private Map<String, Object> variables;

}
