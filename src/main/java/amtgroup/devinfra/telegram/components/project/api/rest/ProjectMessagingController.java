package amtgroup.devinfra.telegram.components.project.api.rest;

import amtgroup.devinfra.telegram.components.project.api.rest.dto.SendProjectMessageRequest;
import amtgroup.devinfra.telegram.components.project.command.ProjectMessagingService;
import amtgroup.devinfra.telegram.components.project.command.dto.SendProjectMessageCommand;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@RestController
@RequestMapping(value = "/api/projects", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "Project Messaging Controller", tags = "project-messaging")
@Slf4j
@RequiredArgsConstructor
public class ProjectMessagingController {

    private final ProjectMessagingService projectMessagingService;

    @ApiOperation("Post direct message to project chat")
    @PostMapping("/{projectKey}/messages")
    public void handle(@PathVariable("projectKey") ProjectKey projectKey, @RequestBody SendProjectMessageRequest request) {
        projectMessagingService.sendMessage(new SendProjectMessageCommand(
                projectKey,
                request.getMessage()
        ));
    }

}
