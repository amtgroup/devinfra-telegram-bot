package amtgroup.devinfra.telegram.components.jira.api.rest;

import amtgroup.devinfra.telegram.components.jira.command.JiraWebhookCommandService;
import amtgroup.devinfra.telegram.components.jira.command.dto.HandleJiraWebhookEventCommand;
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

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/jira", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "Jira Web Hooks", tags = "jira-web-hooks")
@Slf4j
public class JiraController {

    private final JiraWebhookCommandService jiraWebhookCommandService;


    @Autowired
    public JiraController(JiraWebhookCommandService jiraWebhookCommandService) {
        this.jiraWebhookCommandService = jiraWebhookCommandService;
    }


    @ApiOperation("WebHook-уведомления от JIRA")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event) {
        jiraWebhookCommandService.handle(new HandleJiraWebhookEventCommand(
                event
        ));
    }

}
