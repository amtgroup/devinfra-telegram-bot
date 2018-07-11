package amtgroup.devinfra.telegram.config;

import amtgroup.devinfra.telegram.components.template.config.MessageTemplateConfiguration;
import amtgroup.devinfra.telegram.components.template.query.MessageTemplateQueryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.thymeleaf.TemplateEngine;

/**
 * @author Vitaly Ogoltsov
 */
@TestConfiguration
@Import(MessageTemplateConfiguration.class)
public class MessageTemplateTestConfiguration {

    @Bean
    public MessageTemplateQueryService messageTemplateQueryService(TemplateEngine templateEngine) {
        return new MessageTemplateQueryService(templateEngine);
    }

}
