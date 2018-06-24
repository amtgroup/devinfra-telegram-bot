package amtgroup.devinfra.telegram.components.telegram.command.dto;

import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendTelegramMessageCommand {

    @NotNull
    @Valid
    private TelegramChatId chatId;

    @NotBlank
    private String message;

}
