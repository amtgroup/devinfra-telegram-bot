package amtgroup.devinfra.telegram.components.telegram.config;

import lombok.Data;
import org.apache.http.client.config.RequestConfig;
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
     * Настройка для {@link RequestConfig#getConnectTimeout()} ()}.
     */
    private int connectTimeout = 75000;
    /**
     * Настройка для {@link RequestConfig#getSocketTimeout()}.
     */
    private int socketTimeout = 75000;
    /**
     * Настройка для {@link RequestConfig#getConnectionRequestTimeout()} ()}.
     */
    private int requestTimeout = 75000;

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
         * Схема для подключения proxy.
         */
        private String scheme;

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
