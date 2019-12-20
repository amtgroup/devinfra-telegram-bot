package amtgroup.devinfra.telegram.components.project.command;

import amtgroup.devinfra.telegram.components.project.command.dto.SendProjectMessageCommand;
import amtgroup.devinfra.telegram.components.project.exception.TelegramChatIdNotSetForProjectException;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryService;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQuery;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQueryResult;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import amtgroup.devinfra.telegram.components.telegram.command.TelegramCommandService;
import amtgroup.devinfra.telegram.components.telegram.command.dto.SendTelegramMessageCommand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
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

import java.util.Objects;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = ProjectMessagingServiceTest.TestContextConfiguration.class,
        initializers = ConfigFileApplicationContextInitializer.class
)
@ActiveProfiles("test")
public class ProjectMessagingServiceTest {

    @MockBean
    private ProjectCatalogQueryService projectCatalogQueryService;
    @MockBean
    private TelegramCommandService telegramCommandService;

    @Autowired
    private ProjectMessagingService projectMessagingService;


    @Test
    public void givenTelegramChatIdSetForProject_whenMessageIsSent_thenOk() {
        ProjectKey projectKey = ProjectKey.of("project1");
        TelegramChatId telegramChatId = TelegramChatId.of(0L);

        ArgumentCaptor<FindTelegramChatIdByProjectKeyQuery> findTelegramChatIdByProjectKeyQueryArgumentCaptor = ArgumentCaptor.forClass(FindTelegramChatIdByProjectKeyQuery.class);
        given(projectCatalogQueryService.handle(findTelegramChatIdByProjectKeyQueryArgumentCaptor.capture()))
                .will((Answer<FindTelegramChatIdByProjectKeyQueryResult>) invocation -> new FindTelegramChatIdByProjectKeyQueryResult(
                        Objects.equals(invocation.getArgument(0, FindTelegramChatIdByProjectKeyQuery.class).getProjectKey(), projectKey) ? telegramChatId : null
                ));

        ArgumentCaptor<SendTelegramMessageCommand> sendTelegramMessageCommandArgumentCaptor = ArgumentCaptor.forClass(SendTelegramMessageCommand.class);
        doNothing().when(telegramCommandService).sendMessage(sendTelegramMessageCommandArgumentCaptor.capture());

        projectMessagingService.sendMessage(new SendProjectMessageCommand(
                projectKey,
                "message"
        ));

        verify(projectCatalogQueryService, times(1)).handle(isA(FindTelegramChatIdByProjectKeyQuery.class));
        verifyNoMoreInteractions(projectCatalogQueryService);

        verify(telegramCommandService, times(1)).sendMessage(isA(SendTelegramMessageCommand.class));
        verifyNoMoreInteractions(telegramCommandService);
        Assert.assertEquals(
                telegramChatId,
                sendTelegramMessageCommandArgumentCaptor.getValue().getChatId()
        );
        Assert.assertEquals(
                "message",
                sendTelegramMessageCommandArgumentCaptor.getValue().getMessage()
        );
    }

    @Test(expected = TelegramChatIdNotSetForProjectException.class)
    public void givenTelegramChatIdNotSetForProject_whenMessageIsSent_thenError() {
        given(projectCatalogQueryService.handle(isA(FindTelegramChatIdByProjectKeyQuery.class)))
                .willReturn(new FindTelegramChatIdByProjectKeyQueryResult(null));
        projectMessagingService.sendMessage(new SendProjectMessageCommand(
                ProjectKey.of("project1"),
                "message"
        ));
        verifyNoInteractions(telegramCommandService);
    }


    @TestConfiguration
    @Import({
            ValidationAutoConfiguration.class,
            JacksonAutoConfiguration.class
    })
    static class TestContextConfiguration {

        @Bean
        public ProjectMessagingService projectMessagingService(ProjectCatalogQueryService projectCatalogQueryService,
                                                               TelegramCommandService telegramCommandService) {

            return new ProjectMessagingService(
                    projectCatalogQueryService,
                    telegramCommandService
            );
        }

    }

}
