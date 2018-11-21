package amtgroup.devinfra.telegram.components.template.config;

import amtgroup.devinfra.telegram.components.template.query.MessageTemplateQueryService;
import amtgroup.devinfra.telegram.components.template.util.Markdown;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@ImportAutoConfiguration(FreeMarkerAutoConfiguration.class)
@RequiredArgsConstructor
public class MessageTemplateConfiguration {

    @Bean
    public FreeMarkerGlobalVariablesConfigurer freeMarkerGlobalVariablesConfigurer() {
        Map<String, Object> globalVariables = new HashMap<>();
        globalVariables.put("markdown", new Markdown());
        globalVariables.put("strings", new StringUtils());
        return new FreeMarkerGlobalVariablesConfigurer(globalVariables);
    }

    @Bean
    public MessageTemplateQueryService messageTemplateQueryService(freemarker.template.Configuration freemarker,
                                                                   FreeMarkerProperties freeMarkerProperties) {

        return new MessageTemplateQueryService(
                freemarker,
                freeMarkerProperties.getSuffix()
        );
    }


    static class FreeMarkerGlobalVariablesConfigurer implements BeanPostProcessor {

        private final Map<String, Object> globalVariables;


        FreeMarkerGlobalVariablesConfigurer(Map<String, Object> globalVariables) {
            this.globalVariables = new HashMap<>(globalVariables);
        }


        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof FreeMarkerConfigurationFactory) {
                ((FreeMarkerConfigurationFactory) bean).setFreemarkerVariables(globalVariables);
            }
            return bean;
        }

    }

}
