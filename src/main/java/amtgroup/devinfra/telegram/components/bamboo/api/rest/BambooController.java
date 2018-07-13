package amtgroup.devinfra.telegram.components.bamboo.api.rest;

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

import java.util.Collections;
import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/bamboo", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "Bamboo Web Hooks", tags = "bamboo-web-hooks")
@Slf4j
public class BambooController {

    private final ServiceKey serviceKey = ServiceKey.of("bamboo");
    private final NotificationCommandService notificationCommandService;


    @Autowired
    public BambooController(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    @ApiOperation("WebHook-уведомления от Bamboo")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event) {
        ProjectKey projectKey = Optional.of(event.path("issue").path("fields").path("project").path("key"))
                .map(JsonNode::textValue)
                .map(ProjectKey::of)
                .orElse(null);
        EventTypeId eventTypeId = Optional.of(event.path("webhookEvent"))
                .map(JsonNode::textValue)
                .map(EventTypeId::of)
                .orElse(null);
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                projectKey,
                eventTypeId,
                Collections.singletonMap("event", event)
        ));
    }

}
