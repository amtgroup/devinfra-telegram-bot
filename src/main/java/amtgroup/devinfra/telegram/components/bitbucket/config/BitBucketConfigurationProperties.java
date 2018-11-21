package amtgroup.devinfra.telegram.components.bitbucket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@ConfigurationProperties(prefix = "bitbucket")
@Data
public class BitBucketConfigurationProperties {

    /**
     * URL до инсталляции.
     */
    private String url = "https://git.example.com";

}
