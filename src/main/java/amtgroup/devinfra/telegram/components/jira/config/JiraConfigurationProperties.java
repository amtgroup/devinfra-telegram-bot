package amtgroup.devinfra.telegram.components.jira.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@ConfigurationProperties(prefix = "jira")
@Data
public class JiraConfigurationProperties {

    /**
     * URL до инсталляции.
     */
    private String url = "https://jira.example.com";

}
