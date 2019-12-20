package amtgroup.devinfra.telegram.components.project.command;

import amtgroup.devinfra.telegram.components.project.command.dto.SendProjectMessageCommand;
import amtgroup.devinfra.telegram.components.project.exception.TelegramChatIdNotSetForProjectException;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryService;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQuery;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import amtgroup.devinfra.telegram.components.telegram.command.TelegramCommandService;
import amtgroup.devinfra.telegram.components.telegram.command.dto.SendTelegramMessageCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class ProjectMessagingService {

    private final ProjectCatalogQueryService projectCatalogQueryService;
    private final TelegramCommandService telegramCommandService;


    public void sendMessage(@NotNull @Valid SendProjectMessageCommand command) {
        TelegramChatId telegramChatId = projectCatalogQueryService.handle(new FindTelegramChatIdByProjectKeyQuery(
                command.getProjectKey()
        )).getTelegramChatId();
        if (telegramChatId == null) {
            throw new TelegramChatIdNotSetForProjectException(command.getProjectKey());
        }
        telegramCommandService.sendMessage(new SendTelegramMessageCommand(
                telegramChatId,
                command.getMessage()
        ));
    }

}
