package amtgroup.devinfra.telegram.components.gitlab.command;

import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyCommitCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyIssueCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestCloseEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestMergeEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestOpenEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestReopenEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifySnippetCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.config.GitlabConfigurationProperties;
import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.notification.command.dto.SendNotificationCommand;
import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Lukyanets
 */
@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class GitlabNotificationCommandService {

    private final ServiceKey serviceKey = ServiceKey.of("gitlab");

    private final GitlabConfigurationProperties gitlabConfigurationProperties;

    private final NotificationCommandService notificationCommandService;

    public void handle(NotifyMergeRequestOpenEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("merge-request/open"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyMergeRequestCloseEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("merge-request/close"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyMergeRequestReopenEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("merge-request/reopen"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyMergeRequestMergeEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("merge-request/merge"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyMergeRequestCommentEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("merge-request/comment"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyCommitCommentEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("commit/comment"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifyIssueCommentEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("issue/comment"),
                variables(command.getEvent())
        ));
    }

    public void handle(NotifySnippetCommentEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getProject().getKey(),
                EventTypeId.of("snippet/comment"),
                variables(command.getEvent())
        ));
    }
    
    
    private Map<String, Object> variables(GitlabWebhookEvent event) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("gitlab", gitlabConfigurationProperties);
        variables.put("event", event);
        return variables;
    }

}
