package amtgroup.devinfra.telegram.components.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.ApiConstants;

/**
 * @author Vitaly Ogoltsov
 */
@ConfigurationProperties(prefix = "telegram.bot")
@Data
public class TelegramBotProperties {

    /**
     * Имя пользователя бота.
     */
    private String username;
    /**
     * Ключ (token) текущего бота.
     */
    private String token;

    /**
     * Базовый URL для сервисов Telegram.
     */
    private String baseUrl = ApiConstants.BASE_URL;

    /**
     * Максимальное количество потоков, которой запускается для работы с Telegram API.
     */
    private Integer maxThreads = 1;

    /**
     * Настройки HTTP-proxy.
     */
    private HttpProxy proxy;


    @Data
    public static class HttpProxy {

        private String host;

        private Integer port;

        private String scheme;

    }

}
