package amtgroup.devinfra.telegram.components.telegram.config;

import amtgroup.devinfra.telegram.components.telegram.bot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import javax.annotation.PostConstruct;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
@Slf4j
public class TelegramBotConfiguration {

    @PostConstruct
    protected void init() {
        ApiContextInitializer.init();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

    @Bean
    public DefaultBotOptions defaultBotOptions(TelegramBotProperties telegramBotProperties) {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        RequestConfig.Builder requestConfig = RequestConfig.custom()
                .setConnectTimeout(telegramBotProperties.getConnectTimeout())
                .setSocketTimeout(telegramBotProperties.getSocketTimeout())
                .setConnectionRequestTimeout(telegramBotProperties.getRequestTimeout());
        if (telegramBotProperties.getBaseUrl() != null) {
            defaultBotOptions.setBaseUrl(telegramBotProperties.getBaseUrl());
        }
        if (telegramBotProperties.getMaxThreads() != null) {
            defaultBotOptions.setMaxThreads(telegramBotProperties.getMaxThreads());
        }
        if (telegramBotProperties.getProxy() != null) {
            TelegramBotProperties.HttpProxy httpProxyConfiguration = telegramBotProperties.getProxy();
            defaultBotOptions.setHttpProxy(
                    new HttpHost(
                            httpProxyConfiguration.getHost(),
                            httpProxyConfiguration.getPort(),
                            httpProxyConfiguration.getScheme()
                    )
            );
            if (telegramBotProperties.getProxy().getUsername() != null) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(
                        new AuthScope(httpProxyConfiguration.getHost(), httpProxyConfiguration.getPort(), null, httpProxyConfiguration.getScheme()),
                        new UsernamePasswordCredentials(httpProxyConfiguration.getUsername(), httpProxyConfiguration.getPassword())
                );
                defaultBotOptions.setCredentialsProvider(credentialsProvider);
                requestConfig = requestConfig
                        .setProxy(defaultBotOptions.getHttpProxy())
                        .setAuthenticationEnabled(true);
            } else {
                requestConfig = requestConfig
                        .setProxy(defaultBotOptions.getHttpProxy())
                        .setAuthenticationEnabled(false);
            }
        }
        defaultBotOptions.setRequestConfig(requestConfig.build());
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

    @Bean
    public ApplicationRunner configureTelegramBots(TelegramBotsApi telegramBotsApi, TelegramBot telegramBot) {
        return args -> {
            log.info("Регистрация telegram-бота...");
            try {
                telegramBotsApi.registerBot(telegramBot);
            } catch (Exception e) {
                log.error("Ошибка при регистрации telegram-бота", e);
                throw e;
            }
            log.info("Telegram-бот запущен.");
        };
    }

}
