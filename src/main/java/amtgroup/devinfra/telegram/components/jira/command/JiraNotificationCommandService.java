package amtgroup.devinfra.telegram.components.jira.command;

import amtgroup.devinfra.telegram.components.jira.command.dto.SendJiraIssueCreatedEventCommand;
import amtgroup.devinfra.telegram.components.jira.command.dto.SendJiraIssueUpdatedEventCommand;
import amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent;
import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.notification.command.dto.SendNotificationCommand;
import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class JiraNotificationCommandService {

    private final ServiceKey serviceKey = ServiceKey.of("jira");

    private final NotificationCommandService notificationCommandService;


    @Autowired
    public JiraNotificationCommandService(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    public void handle(@NotNull @Valid SendJiraIssueCreatedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                Optional.of(command.getEvent())
                        .map(JiraIssueWebhookEvent::getIssue)
                        .map(JiraIssueWebhookEvent.Issue::getFields)
                        .map(JiraIssueWebhookEvent.IssueFields::getProject)
                        .map(JiraIssueWebhookEvent.Project::getKey)
                        .orElse(null),
                EventTypeId.of("issue_created"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(@NotNull @Valid SendJiraIssueUpdatedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                Optional.of(command.getEvent())
                        .map(JiraIssueWebhookEvent::getIssue)
                        .map(JiraIssueWebhookEvent.Issue::getFields)
                        .map(JiraIssueWebhookEvent.IssueFields::getProject)
                        .map(JiraIssueWebhookEvent.Project::getKey)
                        .orElse(null),
                EventTypeId.of("issue_updated"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

}
