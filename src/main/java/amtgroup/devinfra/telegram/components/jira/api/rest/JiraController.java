package amtgroup.devinfra.telegram.components.jira.api.rest;

import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.notification.command.dto.SendNotificationCommand;
import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/jira", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "Jira Web Hooks", tags = "jira-web-hooks")
@Slf4j
public class JiraController {

    private final ServiceKey serviceKey = new ServiceKey("jira");
    private final NotificationCommandService notificationCommandService;

    private final EventTypeId genericEventTypeId = new EventTypeId("jira:generic");


    @Autowired
    public JiraController(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    @ApiOperation("WebHook-уведомления от JIRA")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event) {
        ProjectKey projectKey = Optional.of(event.path("issue").path("fields").path("project").path("key"))
                .map(JsonNode::textValue)
                .map(ProjectKey::new)
                .orElse(null);
        EventTypeId eventTypeId = Optional.of(event.path("webhookEvent"))
                .map(JsonNode::textValue)
                .map(EventTypeId::new)
                .orElse(genericEventTypeId);
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                projectKey,
                eventTypeId,
                event
        ));
    }

}
