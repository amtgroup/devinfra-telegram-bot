package amtgroup.devinfra.telegram.components.project.config;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryRepository;
import amtgroup.devinfra.telegram.components.project.query.ProjectCatalogQueryService;
import amtgroup.devinfra.telegram.components.project.query.model.Project;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
@EnableConfigurationProperties(ProjectCatalogProperties.class)
public class ProjectCatalogConfiguration {

    @Bean
    public ProjectCatalogQueryRepository projectQueryRepository(ProjectCatalogProperties projectCatalogProperties) {
        Collection<Project> projects = new HashSet<>();
        for (Map.Entry<ProjectKey, ProjectCatalogProperties.Project> entry : projectCatalogProperties.getProjects().entrySet()) {
            projects.add(new Project(
                    entry.getKey(),
                    entry.getValue().getTelegramChatId()
            ));
        }
        return new ProjectCatalogQueryRepository(projects);
    }

    @Bean
    public ProjectCatalogQueryService projectQueryService(ProjectCatalogQueryRepository projectCatalogQueryRepository) {
        return new ProjectCatalogQueryService(projectCatalogQueryRepository);
    }

}
