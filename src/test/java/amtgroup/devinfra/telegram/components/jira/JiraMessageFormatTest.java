package amtgroup.devinfra.telegram.components.jira;

import amtgroup.devinfra.telegram.components.jira.command.JiraNotificationCommandService;
import amtgroup.devinfra.telegram.components.jira.command.JiraWebhookCommandService;
import amtgroup.devinfra.telegram.components.jira.command.dto.HandleJiraWebhookEventCommand;
import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryService;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQuery;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQueryResult;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import amtgroup.devinfra.telegram.components.telegram.command.TelegramCommandService;
import amtgroup.devinfra.telegram.components.telegram.command.dto.SendTelegramMessageCommand;
import amtgroup.devinfra.telegram.components.template.config.MessageTemplateConfiguration;
import amtgroup.devinfra.telegram.components.template.query.MessageTemplateQueryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Vitaly Ogoltsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = JiraMessageFormatTest.TestContextConfiguration.class,
        initializers = ConfigFileApplicationContextInitializer.class
)
@ActiveProfiles("test")
public class JiraMessageFormatTest {

    @MockBean
    private ProjectCatalogQueryService projectCatalogQueryService;
    @MockBean
    private TelegramCommandService telegramCommandService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JiraWebhookCommandService jiraWebhookCommandService;


    @Before
    public void before() {
        given(projectCatalogQueryService.handle(isA(FindTelegramChatIdByProjectKeyQuery.class)))
                .willReturn(new FindTelegramChatIdByProjectKeyQueryResult(TelegramChatId.of(0L)));
    }


    @Test
    public void givenStoryCreated_thenOk() throws Exception {
        validateNotificationMessageSent("issue_created/new-story");
    }

    @Test
    public void givenBugUpdated_andAssigneeNotUdpated_andStatusNotUpdated_thenNoMessageIsSent() throws Exception {
        validateNotificationSkipped("issue_updated/assignee-and-status-unchanged");
    }

    @Test
    public void givenBugUpdated_andStatusUpdated() throws Exception {
        validateNotificationMessageSent("issue_updated/status-updated");
    }

    @Test
    public void givenBugUpdated_andAssigneeUpdated() throws Exception {
        validateNotificationMessageSent("issue_updated/assignee-updated");
    }


    @SuppressWarnings("SameParameterValue")
    private void validateNotificationMessageSent(String testCase) throws Exception {
        ArgumentCaptor<SendTelegramMessageCommand> sendTelegramMessageCommandArgumentCaptor = ArgumentCaptor.forClass(SendTelegramMessageCommand.class);
        doNothing().when(telegramCommandService).sendMessage(sendTelegramMessageCommandArgumentCaptor.capture());
        jiraWebhookCommandService.handle(new HandleJiraWebhookEventCommand(
                objectMapper.readValue(
                        getResourceAsString("notifications/jira/" + testCase + ".json"),
                        JsonNode.class
                )
        ));
        verify(telegramCommandService).sendMessage(any(SendTelegramMessageCommand.class));
        Assert.assertEquals(
                getResourceAsString("notifications/jira/" + testCase + ".txt"),
                sendTelegramMessageCommandArgumentCaptor.getValue().getMessage()
        );
    }

    @SuppressWarnings("SameParameterValue")
    private void validateNotificationSkipped(String testCase) throws Exception {
        doNothing().when(telegramCommandService).sendMessage(isA(SendTelegramMessageCommand.class));
        jiraWebhookCommandService.handle(new HandleJiraWebhookEventCommand(
                objectMapper.readValue(
                        getResourceAsString("notifications/jira/" + testCase + ".json"),
                        JsonNode.class
                )
        ));
        verify(telegramCommandService, never()).sendMessage(any(SendTelegramMessageCommand.class));
    }


    /**
     * Читает и возвращает содержимое файла ресурсов как строку.
     */
    private String getResourceAsString(String name) throws IOException {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(name)) {
            return IOUtils.toString(resourceStream, StandardCharsets.UTF_8);
        }
    }


    @TestConfiguration
    @Import({
            MessageTemplateConfiguration.class,
            ValidationAutoConfiguration.class,
            JacksonAutoConfiguration.class
    })
    static class TestContextConfiguration {

        @Bean
        public NotificationCommandService notificationCommandService(ProjectCatalogQueryService projectCatalogQueryService,
                                                                     MessageTemplateQueryService messageTemplateQueryService,
                                                                     TelegramCommandService telegramCommandService) {

            return new NotificationCommandService(
                    projectCatalogQueryService,
                    messageTemplateQueryService,
                    telegramCommandService
            );
        }

        @Bean
        public JiraNotificationCommandService jiraNotificationCommandService(NotificationCommandService notificationCommandService) {
            return new JiraNotificationCommandService(notificationCommandService);
        }

        @Bean
        public JiraWebhookCommandService jiraWebhookCommandService(ObjectMapper objectMapper,
                                                                   JiraNotificationCommandService jiraNotificationCommandService) {

            return new JiraWebhookCommandService(objectMapper, jiraNotificationCommandService);
        }

    }

}
