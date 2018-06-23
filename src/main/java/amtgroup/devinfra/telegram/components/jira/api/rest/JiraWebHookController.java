package amtgroup.devinfra.telegram.components.jira.api.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JiraWebHookController {

    @ApiOperation("WebHook-уведомления от JIRA")
    @PostMapping("/**")
    public void register(@RequestBody JsonNode event) {
        log.warn("Получено уведомление от JIRA: {}", event);
    }

}
