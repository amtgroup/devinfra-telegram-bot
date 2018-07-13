package amtgroup.devinfra.telegram.components.bitbucket.command;

import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestApprovedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestCommentAddedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestCommentDeletedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestCommentEditedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestDeclinedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestDeletedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestMergedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestNeedsWorkEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestOpenedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyPullRequestUnapprovedEventCommand;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.NotifyRepositoryRefsChangedCommand;
import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.notification.command.dto.SendNotificationCommand;
import amtgroup.devinfra.telegram.components.notification.model.EventTypeId;
import amtgroup.devinfra.telegram.components.project.model.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class BitbucketNotificationCommandService {

    private final ServiceKey serviceKey = ServiceKey.of("bitbucket");

    private final NotificationCommandService notificationCommandService;


    @Autowired
    public BitbucketNotificationCommandService(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    public void handle(NotifyRepositoryRefsChangedCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getRepository().getProject().getKey(),
                EventTypeId.of("repository/refs-changed"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestOpenedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/opened"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestMergedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/merged"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestDeclinedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/declined"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestDeletedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/deleted"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestApprovedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/approved"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestUnapprovedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/unapproved"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestNeedsWorkEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request/needs-work"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestCommentAddedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request-comment/added"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestCommentEditedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request-comment/edited"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

    public void handle(NotifyPullRequestCommentDeletedEventCommand command) {
        notificationCommandService.handle(new SendNotificationCommand(
                serviceKey,
                command.getEvent().getPullRequest().getToRef().getRepository().getProject().getKey(),
                EventTypeId.of("pull-request-comment/deleted"),
                Collections.singletonMap("event", command.getEvent())
        ));
    }

}
