package amtgroup.devinfra.telegram.components.gitlab.api.rest;

import amtgroup.devinfra.telegram.components.gitlab.command.GitlabWebhookCommandService;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.HandleGitlabWebhookEventCommand;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Lukyanets
 */
@RestController
@RequestMapping(value = "/api/gitlab", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(tags = "gitlab-web-hooks")
@SwaggerDefinition(tags = {
        @Tag(name = "gitlab-web-hooks", description = "Gitlab Web Hooks")
})
@Slf4j
public class GitlabController {

    private final GitlabWebhookCommandService gitlabWebhookCommandService;


    @Autowired
    public GitlabController(GitlabWebhookCommandService gitlabWebhookCommandService) {
        this.gitlabWebhookCommandService = gitlabWebhookCommandService;
    }


    @ApiOperation("WebHook-уведомления от Gitlab")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event) {
        gitlabWebhookCommandService.handle(new HandleGitlabWebhookEventCommand(
                event
        ));
    }

}
