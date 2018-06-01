package amtgroup.devinfra.telegram.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.RawConstructor;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer, ServletContextAware {

    @Value("${spring.application.name}")
    private String applicationName;

    private ServletContext servletContext;


    @Value("${springfox.documentation.swagger.ui.enabled:true}")
    private boolean swaggerUiEnabled;
    @Value("${springfox.documentation.swagger.ui.path:}")
    private String swaggerUiPath;
    @Value("${springfox.documentation.swagger.api.host:}")
    private String host;
    @Value("${springfox.documentation.swagger.api.path:}")
    private String path;


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (swaggerUiEnabled) {
            if (StringUtils.isNotBlank(swaggerUiPath)) {
                registry.addViewController(swaggerUiPath + "/v2/api-docs")
                        .setViewName("forward:/v2/api-docs");
                registry.addViewController(swaggerUiPath + "/swagger-resources/configuration/ui")
                        .setViewName("forward:/swagger-resources/configuration/ui");
                registry.addViewController(swaggerUiPath + "/swagger-resources/configuration/security")
                        .setViewName("forward:/swagger-resources/configuration/security");
                registry.addViewController(swaggerUiPath + "/swagger-resources")
                        .setViewName("forward:/swagger-resources");
            }
        }
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerUiEnabled) {
            registry.addResourceHandler(swaggerUiPath + "/swagger-ui.html**")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler(swaggerUiPath + "/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }


    @Bean
    @ConditionalOnMissingBean(ApiInfo.class)
    public ApiInfo apiInfo() {
        return new ApiInfo(
                applicationName,
                null,
                "1.0.0",
                null,
                new Contact(
                        null,
                        null,
                        null
                ),
                null,
                null,
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api(ApiInfo apiInfo, TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .pathProvider(
                        Optional.ofNullable(path)
                                .map(StringUtils::trimToNull)
                                .map(path -> new RelativePathProvider(servletContext) {
                                    @Override
                                    public String getApplicationBasePath() {
                                        return path;
                                    }
                                })
                                .orElse(null)
                )
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .apiInfo(apiInfo)
                .alternateTypeRules(
                        new StringTypeRule(typeResolver)
                );
    }

    @SuppressWarnings("Guava")
    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error"));
    }


    /**
     * Правило для трансляции типов, которые могут быть распарсены из {@link String}.
     */
    private static class StringTypeRule extends AlternateTypeRule {
        StringTypeRule(TypeResolver typeResolver) {
            this(typeResolver, Ordered.LOWEST_PRECEDENCE);
        }

        StringTypeRule(TypeResolver typeResolver, int order) {
            super(typeResolver.resolve(Object.class), typeResolver.resolve(String.class), order);
        }

        @Override
        public ResolvedType alternateFor(ResolvedType type) {
            return appliesTo(type) ? alternate : type;
        }

        @Override
        public boolean appliesTo(ResolvedType type) {
            return type.getConstructors().stream().map(RawConstructor::getRawMember).anyMatch(this::isStringConstructor);
        }

        private boolean isStringConstructor(Constructor constructor) {
            return constructor.getParameterTypes().length == 1 && String.class.equals(constructor.getParameterTypes()[0]);
        }
    }
}
