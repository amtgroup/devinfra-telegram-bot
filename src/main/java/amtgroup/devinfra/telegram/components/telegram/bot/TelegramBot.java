package amtgroup.devinfra.telegram.components.telegram.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

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
        log.warn("Получено обновление от сервера: {}", update);
    }

}
