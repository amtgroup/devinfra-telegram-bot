package amtgroup.devinfra.telegram.components.bitbucket;

import amtgroup.devinfra.telegram.components.bitbucket.command.BitbucketNotificationCommandService;
import amtgroup.devinfra.telegram.components.bitbucket.command.BitbucketWebhookCommandService;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.HandleBitbucketWebhookEventCommand;
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
        classes = BitbucketMessageFormatTest.TestContextConfiguration.class,
        initializers = ConfigFileApplicationContextInitializer.class
)
@ActiveProfiles("test")
public class BitbucketMessageFormatTest {

    @MockBean
    private ProjectCatalogQueryService projectCatalogQueryService;
    @MockBean
    private TelegramCommandService telegramCommandService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BitbucketWebhookCommandService bitbucketWebhookCommandService;


    @Before
    public void before() {
        given(projectCatalogQueryService.handle(isA(FindTelegramChatIdByProjectKeyQuery.class)))
                .willReturn(new FindTelegramChatIdByProjectKeyQueryResult(TelegramChatId.of(0L)));
    }


    @Test
    public void givenRepositoryRefsChanged_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("repository/refs-changed");
    }

    @Test
    public void givenPullRequestOpened_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/opened");
    }

    @Test
    public void givenPullRequestMerged_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/merged");
    }

    @Test
    public void givenPullRequestDeclined_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/declined");
    }

    @Test
    public void givenPullRequestDeleted_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/deleted");
    }

    @Test
    public void givenPullRequestApproved_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/approved");
    }

    @Test
    public void givenPullRequestUnapproved_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/unapproved");
    }

    @Test
    public void givenPullRequestNeedsWork_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request/needs-work");
    }

    @Test
    public void givenPullRequestCommentAdded_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request-comment/added");
    }

    @Test
    public void givenPullRequestCommentEdited_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request-comment/edited");
    }

    @Test
    public void givenPullRequestCommentDelete_thenNotificationSent() throws Exception {
        validateNotificationMessageSent("pull-request-comment/deleted");
    }


    @SuppressWarnings("SameParameterValue")
    private void validateNotificationMessageSent(String testCase) throws Exception {
        ArgumentCaptor<SendTelegramMessageCommand> sendTelegramMessageCommandArgumentCaptor = ArgumentCaptor.forClass(SendTelegramMessageCommand.class);
        doNothing().when(telegramCommandService).sendMessage(sendTelegramMessageCommandArgumentCaptor.capture());
        bitbucketWebhookCommandService.handle(new HandleBitbucketWebhookEventCommand(
                objectMapper.readValue(
                        getResourceAsString("notifications/bitbucket/" + testCase + ".json"),
                        JsonNode.class
                )
        ));
        verify(telegramCommandService).sendMessage(any(SendTelegramMessageCommand.class));
        Assert.assertEquals(
                getResourceAsString("notifications/bitbucket/" + testCase + ".txt").trim(),
                sendTelegramMessageCommandArgumentCaptor.getValue().getMessage()
        );
    }

    @SuppressWarnings("SameParameterValue")
    private void validateNotificationSkipped(String testCase) throws Exception {
        doNothing().when(telegramCommandService).sendMessage(isA(SendTelegramMessageCommand.class));
        bitbucketWebhookCommandService.handle(new HandleBitbucketWebhookEventCommand(
                objectMapper.readValue(
                        getResourceAsString("notifications/bitbucket/" + testCase + ".json"),
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
        public BitbucketNotificationCommandService bitbucketNotificationCommandService(NotificationCommandService notificationCommandService) {
            return new BitbucketNotificationCommandService(notificationCommandService);
        }

        @Bean
        public BitbucketWebhookCommandService bitbucketWebhookCommandService(ObjectMapper objectMapper,
                                                                   BitbucketNotificationCommandService bitbucketNotificationCommandService) {

            return new BitbucketWebhookCommandService(objectMapper, bitbucketNotificationCommandService);
        }

    }

}
