package amtgroup.devinfra.telegram.components.jira.api.rest;

import amtgroup.devinfra.telegram.components.jira.api.rest.dto.JiraIssueEventRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/web-hooks/jira", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "Jira Web Hooks", tags = "jira-web-hooks")
public class JiraWebHookController {

    @ApiOperation("")
    @PostMapping("/{projectKey}/{issueKey}")
    public void postIssueEvent(@RequestBody JiraIssueEventRequest request) {
        // todo
    }

}
