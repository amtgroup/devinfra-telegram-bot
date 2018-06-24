package amtgroup.devinfra.telegram.components.project.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@EnableConfigurationProperties(ProjectsProperties.class)
public class ProjectsConfiguration {
}
