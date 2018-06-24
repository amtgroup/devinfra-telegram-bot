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

    private long connectionTimeout = 75000;
    private long requestTimeout = 75000;

    /**
     * Настройки HTTP-proxy.
     */
    private HttpProxy proxy;


    @Data
    public static class HttpProxy {

        /**
         * Адрес сервера.
         */
        private String host;

        /**
         * Порт сервера.
         */
        private Integer port;

        /**
         * Имя пользователя для аутентификации.
         */
        private String username;

        /**
         * Пароль.
         */
        private String password;

    }

}
