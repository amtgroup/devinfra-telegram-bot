package amtgroup.devinfra.telegram.components.gitlab.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Sergey Lukyanets
 */
@ConfigurationProperties(prefix = "gitlab")
@Data
public class GitlabConfigurationProperties {

    /**
     * URL до инсталляции.
     */
    private String url = "https://gitlab.example.com";

}
