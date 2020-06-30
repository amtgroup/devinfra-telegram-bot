package amtgroup.devinfra.telegram.components.gitlab;

import amtgroup.devinfra.telegram.components.gitlab.command.GitlabNotificationCommandService;
import amtgroup.devinfra.telegram.components.gitlab.command.GitlabWebhookCommandService;
import amtgroup.devinfra.telegram.components.gitlab.command.dto.HandleGitlabWebhookEventCommand;
import amtgroup.devinfra.telegram.components.gitlab.config.GitlabConfiguration;
import amtgroup.devinfra.telegram.components.gitlab.config.GitlabConfigurationProperties;
import amtgroup.devinfra.telegram.components.notification.command.NotificationCommandService;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
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

import static amtgroup.devinfra.telegram.Utils.lsFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * @author Sergey Lukyanets
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = GitlabMessageFormatTest.TestContextConfiguration.class,
        initializers = ConfigFileApplicationContextInitializer.class
)
@ActiveProfiles("test")
public class GitlabMessageFormatTest {

    @MockBean
    private ProjectCatalogQueryService projectCatalogQueryService;
    @MockBean
    private TelegramCommandService telegramCommandService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GitlabWebhookCommandService gitlabWebhookCommandService;


    @Before
    public void before() {
        given(projectCatalogQueryService.handle(isA(FindTelegramChatIdByProjectKeyQuery.class)))
                .willReturn(new FindTelegramChatIdByProjectKeyQueryResult(TelegramChatId.of(0L)));
    }


    @Test
    public void givenMergeRequestOpen_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("merge-request/open");
    }

    @Test
    public void givenMergeRequestClose_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("merge-request/close");
    }

    @Test
    public void givenMergeRequestReopen_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("merge-request/reopen");
    }

    @Test
    public void givenMergeRequestComment_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("merge-request/comment");
    }

    @Test
    public void givenMergeRequestMerge_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("merge-request/merge");
    }


    @SuppressWarnings("SameParameterValue")
    private void validateNotificationMessageSent(String testCase) throws Exception {
        ArgumentCaptor<SendTelegramMessageCommand> sendTelegramMessageCommandArgumentCaptor = ArgumentCaptor.forClass(SendTelegramMessageCommand.class);
        doNothing().when(telegramCommandService).sendMessage(sendTelegramMessageCommandArgumentCaptor.capture());
        gitlabWebhookCommandService.handle(new HandleGitlabWebhookEventCommand(
                ProjectKey.of("1"),
                objectMapper.readValue(
                        getResourceAsString("notifications/gitlab/" + testCase + ".json"),
                        JsonNode.class
                )
        ));
        verify(telegramCommandService).sendMessage(any(SendTelegramMessageCommand.class));
        Assert.assertEquals(
                getResourceAsString("notifications/gitlab/" + testCase + ".txt").trim(),
                lsFormat(sendTelegramMessageCommandArgumentCaptor.getValue().getMessage())
        );
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
            GitlabConfiguration.class,
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
        public GitlabNotificationCommandService gitlabNotificationCommandService(GitlabConfigurationProperties gitlabConfigurationProperties,
                                                                                    NotificationCommandService notificationCommandService) {

            return new GitlabNotificationCommandService(gitlabConfigurationProperties, notificationCommandService);
        }

        @Bean
        public GitlabWebhookCommandService gitlabWebhookCommandService(ObjectMapper objectMapper,
                                                                          GitlabNotificationCommandService gitlabNotificationCommandService) {

            return new GitlabWebhookCommandService(objectMapper, gitlabNotificationCommandService);
        }

    }

}
