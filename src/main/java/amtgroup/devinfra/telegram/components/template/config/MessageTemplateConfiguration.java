package amtgroup.devinfra.telegram.components.template.config;

import amtgroup.devinfra.telegram.components.template.query.MessageTemplateQueryService;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@ImportAutoConfiguration(FreeMarkerAutoConfiguration.class)
public class MessageTemplateConfiguration {

    @Bean
    public MessageTemplateQueryService messageTemplateQueryService(freemarker.template.Configuration freemarker,
                                                                   FreeMarkerProperties freemarkerProperties) {

        return new MessageTemplateQueryService(
                freemarker,
                freemarkerProperties.getSuffix()
        );
    }
    
}
