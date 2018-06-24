package amtgroup.devinfra.telegram.components.telegram.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Vitaly Ogoltsov
 */
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;


    public TelegramBot(@NotNull DefaultBotOptions options, String botUsername, String botToken) {
        super(options);
        this.botUsername = Objects.requireNonNull(botUsername);
        this.botToken = Objects.requireNonNull(botToken);
    }


    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Получено обновление от сервера: {}", update);
        Message message = update.getMessage();
        if (message != null) {
            Chat chat = message.getChat();
            TelegramChatId chatId = new TelegramChatId(chat.getId());
            // бот добавлен в новый чат
            if (message.getNewChatMembers() != null && message.getNewChatMembers().stream().anyMatch(user -> Objects.equals(user.getUserName(), getBotUsername()))) {
                try {
                    execute(new SendMessage(
                            chat.getId(),
                            "Добрый день! Укажите идентификатор чата администратору, чтобы включить интеграцию с подсистемами разработки: " + chatId
                    ));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
