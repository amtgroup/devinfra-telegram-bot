package amtgroup.devinfra.telegram.components.template.config;

import amtgroup.devinfra.telegram.components.template.engine.MarkdownDialect;
import amtgroup.devinfra.telegram.components.template.engine.StringUtilsDialect;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@ImportAutoConfiguration(ThymeleafAutoConfiguration.class)
public class MessageTemplateConfiguration {

    @Bean
    public StringUtilsDialect stringUtilsDialect() {
        return new StringUtilsDialect();
    }

    @Bean
    public MarkdownDialect markdownDialect() {
        return new MarkdownDialect();
    }

}
