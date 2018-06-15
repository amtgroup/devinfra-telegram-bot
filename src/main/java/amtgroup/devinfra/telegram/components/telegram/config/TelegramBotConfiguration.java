package amtgroup.devinfra.telegram.components.telegram.config;

import amtgroup.devinfra.telegram.components.telegram.bot.TelegramBot;
import org.apache.http.HttpHost;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotConfiguration {

    @Bean
    public DefaultBotOptions telegramBotOptions(TelegramBotProperties telegramBotProperties) {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        if (telegramBotProperties.getBaseUrl() != null) {
            defaultBotOptions.setBaseUrl(telegramBotProperties.getBaseUrl());
        }
        if (telegramBotProperties.getMaxThreads() != null) {
            defaultBotOptions.setMaxThreads(telegramBotProperties.getMaxThreads());
        }
        if (telegramBotProperties.getProxy() != null) {
            defaultBotOptions.setHttpProxy(
                    new HttpHost(
                            telegramBotProperties.getProxy().getHost(),
                            telegramBotProperties.getProxy().getPort(),
                            telegramBotProperties.getProxy().getScheme()
                    )
            );
        }
        return defaultBotOptions;
    }

    @Bean
    public TelegramBot telegramBot(TelegramBotProperties telegramBotProperties,
                                   DefaultBotOptions telegramBotOptions) {

        return new TelegramBot(
                telegramBotOptions,
                telegramBotProperties.getUsername(),
                telegramBotProperties.getToken()
        );
    }

}
