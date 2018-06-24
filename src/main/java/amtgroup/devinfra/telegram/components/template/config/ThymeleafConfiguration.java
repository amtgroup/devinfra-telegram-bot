package amtgroup.devinfra.telegram.components.template.config;

import amtgroup.devinfra.telegram.components.template.engine.StringUtilsDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
public class ThymeleafConfiguration {

    @Bean
    public StringUtilsDialect stringUtilsDialect() {
        return new StringUtilsDialect();
    }

}
