package amtgroup.devinfra.telegram.components.jira.command;

import amtgroup.devinfra.telegram.components.jira.command.dto.HandleJiraWebhookEventCommand;
import amtgroup.devinfra.telegram.components.jira.command.dto.SendJiraIssueCreatedEventCommand;
import amtgroup.devinfra.telegram.components.jira.command.dto.SendJiraIssueUpdatedEventCommand;
import amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class JiraWebhookCommandService {

    private final ObjectMapper objectMapper;
    private final JiraNotificationCommandService jiraNotificationCommandService;


    @Autowired
    public JiraWebhookCommandService(ObjectMapper objectMapper, JiraNotificationCommandService jiraNotificationCommandService) {
        this.objectMapper = objectMapper;
        this.jiraNotificationCommandService = jiraNotificationCommandService;
    }


    public void handle(@NotNull @Valid HandleJiraWebhookEventCommand command) {
        String webhookEvent = Optional.of(command.getEvent().path("webhookEvent"))
                .map(JsonNode::textValue)
                .orElseThrow(() -> new RuntimeException("Не установлен webhookEvent"));

        switch (webhookEvent) {
            case "jira:issue_created":
                jiraNotificationCommandService.handle(new SendJiraIssueCreatedEventCommand(
                        objectMapper.convertValue(command.getEvent(), JiraIssueWebhookEvent.class)
                ));
                break;

            case "jira:issue_updated":
                jiraNotificationCommandService.handle(new SendJiraIssueUpdatedEventCommand(
                        objectMapper.convertValue(command.getEvent(), JiraIssueWebhookEvent.class)
                ));
                break;

            default:
                log.debug("Webhook event skipped: {}", webhookEvent);
                break;
        }
    }

}
