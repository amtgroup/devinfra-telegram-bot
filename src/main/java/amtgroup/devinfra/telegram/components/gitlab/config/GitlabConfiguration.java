package amtgroup.devinfra.telegram.components.gitlab.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Lukyanets
 */
@Configuration
@EnableConfigurationProperties(GitlabConfigurationProperties.class)
public class GitlabConfiguration {



}
