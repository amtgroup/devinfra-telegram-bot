package amtgroup.devinfra.telegram.components.bitbucket.command;

import amtgroup.devinfra.telegram.components.bitbucket.command.dto.HandleBitbucketWebhookEventCommand;
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
import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestWebhookEvent;
import amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketRepositoryWebhookEvent;
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
public class BitbucketWebhookCommandService {

    private final ObjectMapper objectMapper;
    private final BitbucketNotificationCommandService bitbucketNotificationCommandService;


    @Autowired
    public BitbucketWebhookCommandService(ObjectMapper objectMapper, BitbucketNotificationCommandService bitbucketNotificationCommandService) {
        this.objectMapper = objectMapper;
        this.bitbucketNotificationCommandService = bitbucketNotificationCommandService;
    }


    public void handle(@NotNull @Valid HandleBitbucketWebhookEventCommand command) {
        String webhookEvent = Optional.of(command.getEvent().path("eventKey"))
                .map(JsonNode::textValue)
                .orElseThrow(() -> new RuntimeException("Не установлен eventKey"));

        switch (webhookEvent) {
            case "repo:refs_changed":
                bitbucketNotificationCommandService.handle(new NotifyRepositoryRefsChangedCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketRepositoryWebhookEvent.class)
                ));
                break;

            case "pr:opened":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestOpenedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:merged":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestMergedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:declined":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestDeclinedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:deleted":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestDeletedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:reviewer:approved":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestApprovedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:reviewer:unapproved":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestUnapprovedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:reviewer:needs_work":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestNeedsWorkEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestWebhookEvent.class)
                ));
                break;

            case "pr:comment:added":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestCommentAddedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestCommentWebhookEvent.class)
                ));
                break;

            case "pr:comment:edited":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestCommentEditedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestCommentWebhookEvent.class)
                ));
                break;

            case "pr:comment:deleted":
                bitbucketNotificationCommandService.handle(new NotifyPullRequestCommentDeletedEventCommand(
                        objectMapper.convertValue(command.getEvent(), BitbucketPullRequestCommentWebhookEvent.class)
                ));
                break;

            default:
                log.debug("Webhook event skipped: {}", webhookEvent);
                break;
        }
    }

}
