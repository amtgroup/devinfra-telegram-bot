package amtgroup.devinfra.telegram.components.project.query.model;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import lombok.Value;

/**
 * @author Vitaly Ogoltsov
 */
@Value
public class Project {

    /**
     * Код проекта.
     */
    private ProjectKey projectKey;

    /**
     * Идентификатор telegram-канала.
     */
    private TelegramChatId telegramChatId;

}
