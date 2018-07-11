package amtgroup.devinfra.telegram.components.bitbucket.api.rest;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/bitbucket", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "BitBucket Web Hooks", tags = "bitbucket-web-hooks")
@Slf4j
public class BitBucketController {

    private final ServiceKey serviceKey = ServiceKey.of("bitbucket");
    private final NotificationCommandService notificationCommandService;


    @Autowired
    public BitBucketController(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    @ApiOperation("WebHook-уведомления от BitBucket")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event,
                       @RequestHeader("X-Event-Key") EventTypeId eventTypeId) {

        ProjectKey projectKey = null;
        if (StringUtils.startsWith(eventTypeId.toString(), "repo:")) {
            projectKey = Optional.of(event.path("repository").path("project").path("key"))
                    .map(JsonNode::textValue)
                    .map(ProjectKey::of)
                    .orElse(null);
        } else if (StringUtils.startsWith(eventTypeId.toString(), "pr:")) {
            projectKey = Optional.of(event.path("pullRequest").path("toRef").path("project").path("key"))
                    .map(JsonNode::textValue)
                    .map(ProjectKey::of)
                    .orElse(null);
        }
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                projectKey,
                eventTypeId,
                event
        ));
    }

}
