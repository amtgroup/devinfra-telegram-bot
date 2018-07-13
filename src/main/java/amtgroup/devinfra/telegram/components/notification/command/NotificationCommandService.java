package amtgroup.devinfra.telegram.components.notification.command;

import amtgroup.devinfra.telegram.components.notification.command.dto.SendNotificationCommand;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryService;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQuery;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import amtgroup.devinfra.telegram.components.telegram.command.TelegramCommandService;
import amtgroup.devinfra.telegram.components.telegram.command.dto.SendTelegramMessageCommand;
import amtgroup.devinfra.telegram.components.template.model.MessageTemplateId;
import amtgroup.devinfra.telegram.components.template.query.MessageTemplateQueryService;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQuery;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class NotificationCommandService {

    private final ProjectCatalogQueryService projectCatalogQueryService;
    private final MessageTemplateQueryService messageTemplateQueryService;
    private final TelegramCommandService telegramCommandService;


    public NotificationCommandService(ProjectCatalogQueryService projectCatalogQueryService,
                                      MessageTemplateQueryService messageTemplateQueryService,
                                      TelegramCommandService telegramCommandService) {

        this.projectCatalogQueryService = projectCatalogQueryService;
        this.messageTemplateQueryService = messageTemplateQueryService;
        this.telegramCommandService = telegramCommandService;
    }


    /**
     * Отправить уведомление по проекту.
     */
    public void handle(@NotNull @Valid SendNotificationCommand command) {
        TelegramChatId telegramChatId = projectCatalogQueryService.handle(new FindTelegramChatIdByProjectKeyQuery(
                command.getProjectKey()
        )).getTelegramChatId();
        if (telegramChatId == null) {
            log.trace("[{}]: не установлен идентификатор telegram-канала.", command.getProjectKey());
            return;
        }
        // отформатировать сообщение по шаблону
        FormatMessageQueryResult result = messageTemplateQueryService.formatMessage(new FormatMessageQuery(
                MessageTemplateId.of("notifications/" + command.getServiceKey() + "/" + command.getEventTypeId()),
                command.getVariables()
        ));
        // шаблонизатор может вернуть пустой текст или null,
        // в случае если шаблон "принял решение" не отправлять сообщение
        if (StringUtils.isNotBlank(result.getText())) {
            telegramCommandService.sendMessage(new SendTelegramMessageCommand(
                    telegramChatId,
                    result.getText()
            ));
        }
    }

}
