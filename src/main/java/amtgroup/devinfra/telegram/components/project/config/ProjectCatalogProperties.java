package amtgroup.devinfra.telegram.components.project.config;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.telegram.bot.TelegramChatId;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@ConfigurationProperties
@Data
public class ProjectCatalogProperties {

    private Map<ProjectKey, Project> projects = new HashMap<>();


    @Data
    public static class Project {

        private TelegramChatId telegramChatId;

    }

}
