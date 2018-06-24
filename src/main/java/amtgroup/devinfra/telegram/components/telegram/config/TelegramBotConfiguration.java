package amtgroup.devinfra.telegram.components.telegram.config;

import amtgroup.devinfra.telegram.components.telegram.bot.TelegramBot;
import amtgroup.devinfra.telegram.components.telegram.util.ProxyPlainConnectionSocketFactory;
import amtgroup.devinfra.telegram.components.telegram.util.ProxySSLConnectionSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public TelegramBot telegramBot(TelegramBotProperties telegramBotProperties) {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        if (telegramBotProperties.getBaseUrl() != null) {
            defaultBotOptions.setBaseUrl(telegramBotProperties.getBaseUrl());
        }
        if (telegramBotProperties.getMaxThreads() != null) {
            defaultBotOptions.setMaxThreads(telegramBotProperties.getMaxThreads());
        }
        if (telegramBotProperties.getProxy() != null && telegramBotProperties.getProxy().getUsername() != null) {
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            telegramBotProperties.getProxy().getUsername(),
                            Optional.ofNullable(telegramBotProperties.getProxy().getPassword()).orElse("").toCharArray()
                    );
                }
            });
        }
        TelegramBot telegramBot = new TelegramBot(
                defaultBotOptions,
                telegramBotProperties.getUsername(),
                telegramBotProperties.getToken()
        );
        if (telegramBotProperties.getProxy() != null) {
            InetSocketAddress proxyAddress = new InetSocketAddress(
                    telegramBotProperties.getProxy().getHost(),
                    telegramBotProperties.getProxy().getPort()
            );
            configureSocksProxy(telegramBot, new Proxy(Proxy.Type.SOCKS, proxyAddress));
        }
        return telegramBot;
    }

    @Bean
    public ApplicationRunner configureTelegramBots(TelegramBotsApi telegramBotsApi,
                                                   List<TelegramLongPollingBot> longPollingBots) {
        return args -> {
            for (TelegramLongPollingBot bot : longPollingBots) {
                log.info("Регистрация telegram-бота {}", bot.getClass());
                try {
                    telegramBotsApi.registerBot(bot);
                    log.info("Telegram-бот {} зарегистрирован", bot.getClass());
                } catch (Exception e) {
                    log.error("Ошибка регистрации telegram-бота {}", bot.getClass(), e);
                    throw e;
                }
            }
        };
    }

    private void configureSocksProxy(TelegramBot telegramBot, Proxy proxy) {
        Map<String, ConnectionSocketFactory> map = getSocketFactoryRegistryMap(telegramBot);
        map.put("http", new ProxyPlainConnectionSocketFactory(proxy));
        map.put("https", new ProxySSLConnectionSocketFactory(SSLContexts.createSystemDefault(), proxy));
    }

    private Map<String, ConnectionSocketFactory> getSocketFactoryRegistryMap(DefaultAbsSender bot) {
        Object value = bot;
        for (String name : StringUtils.split("httpclient.connManager.connectionOperator.socketFactoryRegistry.map", '.')) {
            Field field = ReflectionUtils.findField(value.getClass(), name);
            if (field == null) {
                value = null;
                break;
            }
            ReflectionUtils.makeAccessible(field);
            value = ReflectionUtils.getField(field, value);
            if (value == null) {
                break;
            }
        }
        @SuppressWarnings("unchecked")
        Map<String,ConnectionSocketFactory> socketFactoryRegistryMap = (Map<String, ConnectionSocketFactory>) value;
        return socketFactoryRegistryMap;
    }

}
