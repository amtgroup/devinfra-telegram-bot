package amtgroup.devinfra.telegram.components.gitlab.command;

import amtgroup.devinfra.telegram.components.gitlab.command.dto.HandleGitlabWebhookEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyCommitCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyIssueCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestCloseEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestMergeEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestOpenEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyMergeRequestReopenEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyPipelineFailedEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyPipelineManualEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifyPipelineSuccessEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.NotifySnippetCommentEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabCommitCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabIssueCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestCommentWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabPipelineWebhookEvent;
import amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabSnippetCommentWebhookEvent;
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
 * @author Sergey Lukyanets
 */
@Service
@Validated
@Slf4j
public class GitlabWebhookCommandService {

    private final ObjectMapper objectMapper;
    private final GitlabNotificationCommandService gitlabNotificationCommandService;


    @Autowired
    public GitlabWebhookCommandService(ObjectMapper objectMapper, GitlabNotificationCommandService gitlabNotificationCommandService) {
        this.objectMapper = objectMapper;
        this.gitlabNotificationCommandService = gitlabNotificationCommandService;
    }


    public void handle(@NotNull @Valid HandleGitlabWebhookEventCommand command) {
        String webhookEvent = Optional.of(command.getEvent().path("object_kind"))
                .map(JsonNode::textValue)
                .orElseThrow(() -> new RuntimeException("Не установлен object_kind"));

        switch (webhookEvent) {
            case "merge_request":
                String action = Optional.ofNullable(command.getEvent()
                        .path("object_attributes").path("action").textValue())
                        .orElseThrow(() -> new RuntimeException("Не установлен action"));
                switch (action) {
                    case "open":
                        gitlabNotificationCommandService.handle(new NotifyMergeRequestOpenEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabMergeRequestWebhookEvent.class)
                        ));
                        break;
                    case "close":
                        gitlabNotificationCommandService.handle(new NotifyMergeRequestCloseEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabMergeRequestWebhookEvent.class)
                        ));
                        break;
                    case "reopen":
                        gitlabNotificationCommandService.handle(new NotifyMergeRequestReopenEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabMergeRequestWebhookEvent.class)
                        ));
                        break;
                    case "merge":
                        gitlabNotificationCommandService.handle(new NotifyMergeRequestMergeEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabMergeRequestWebhookEvent.class)
                        ));
                        break;
                    default:
                        log.debug("Webhook event skipped: {}.{}", webhookEvent, action);
                        break;
                }
                break;

            case "note":
                String target = Optional.ofNullable(command.getEvent()
                            .path("object_attributes").path("noteable_type").textValue())
                        .orElseThrow(() -> new RuntimeException("Не установлен noteable_type"));
                switch (target) {
                    case "MergeRequest":
                        gitlabNotificationCommandService.handle(new NotifyMergeRequestCommentEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabMergeRequestCommentWebhookEvent.class)
                        ));
                        break;
                    case "Commit":
                        gitlabNotificationCommandService.handle(new NotifyCommitCommentEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabCommitCommentWebhookEvent.class)
                        ));
                        break;
                    case "Issue":
                        gitlabNotificationCommandService.handle(new NotifyIssueCommentEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabIssueCommentWebhookEvent.class)
                        ));
                        break;
                    case "Snippet":
                        gitlabNotificationCommandService.handle(new NotifySnippetCommentEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabSnippetCommentWebhookEvent.class)
                        ));
                        break;
                    default:
                        log.debug("Webhook event skipped: {}.{}", webhookEvent, target);
                        break;
                }
                break;
            case "pipeline":
                String status = Optional.ofNullable(command.getEvent()
                        .path("object_attributes").path("status").textValue())
                        .orElseThrow(() -> new RuntimeException("Не установлен status"));
                switch (status) {
                    case "success":
                        gitlabNotificationCommandService.handle(new NotifyPipelineSuccessEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabPipelineWebhookEvent.class)
                        ));
                        break;
                    case "failed":
                        gitlabNotificationCommandService.handle(new NotifyPipelineFailedEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabPipelineWebhookEvent.class)
                        ));
                        break;
                    case "manual":
                        gitlabNotificationCommandService.handle(new NotifyPipelineManualEventCommand(
                                command.getProjectKey(),
                                objectMapper.convertValue(command.getEvent(), GitlabPipelineWebhookEvent.class)
                        ));
                        break;
                    default:
                        log.debug("Webhook event skipped: {}.{}", webhookEvent, status);
                        break;
                }
                break;
            default:
                log.debug("Webhook event skipped: {}", webhookEvent);
                break;
        }
    }

}
