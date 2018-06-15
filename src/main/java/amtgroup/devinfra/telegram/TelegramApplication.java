package amtgroup.devinfra.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.telegram.telegrambots.starter.EnableTelegramBots;

/**
 * @author Vitaly Ogoltsov
 */
@SpringBootApplication
@EnableFeignClients
@EnableTelegramBots
public class TelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramApplication.class, args);
    }

}
