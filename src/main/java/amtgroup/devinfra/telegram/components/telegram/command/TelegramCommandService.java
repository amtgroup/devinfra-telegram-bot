package amtgroup.devinfra.telegram.components.telegram.command;

import amtgroup.devinfra.telegram.components.telegram.bot.TelegramBot;
import amtgroup.devinfra.telegram.components.telegram.command.dto.SendTelegramMessageCommand;
import amtgroup.devinfra.telegram.components.telegram.exception.TelegramRemoteException;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class TelegramCommandService {

    private static final int MESSAGE_LENGTH_LIMIT = 4096;

    private final TelegramBot telegramBot;


    @Autowired
    public TelegramCommandService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    public void sendMessage(@NotNull @Validated SendTelegramMessageCommand command) {
        try {
            String message = command.getMessage();
            // конвертировать emoji в unicode
            message = EmojiParser.parseToUnicode(message);
            // разбить сообщение и отправить по-фрагментно
            for (String fragment : split(message)) {
                telegramBot.execute(
                        new SendMessage(
                                command.getChatId().longValue(),
                                fragment
                        ).enableMarkdown(true)
                );
            }
        } catch (Exception e) {
            throw new TelegramRemoteException(e);
        }
    }

    /**
     * Разбивает сообщение на фрагменты не более чем по {@link #MESSAGE_LENGTH_LIMIT} символов.
     */
    private List<String> split(String text) {
        List<String> results = new ArrayList<>();
        while (text.length() > MESSAGE_LENGTH_LIMIT) {
            String split = text.substring(0, MESSAGE_LENGTH_LIMIT);
            // найти последнее вхождение разделителя из списка
            int splitIndex = StringUtils.lastIndexOfAny(
                    split,
                    " ",
                    ".",
                    ";",
                    ","
            );
            if (splitIndex >= 0) {
                // разделитель попадает в отделяемый фрагмент
                split = split.substring(0, splitIndex + 1);
            }
            // отрезать фрагмент из общего сообщения
            text = text.substring(split.length());
            // удалить пробелы в начале и конце строки и добавить в результат
            split = StringUtils.trimToNull(split);
            if (split != null) {
                results.add(split);
            }
        }
        results.add(text);
        return results;
    }

}
