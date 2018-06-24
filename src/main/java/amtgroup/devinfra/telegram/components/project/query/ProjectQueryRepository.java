package amtgroup.devinfra.telegram.components.project.query;

import amtgroup.devinfra.telegram.components.project.config.ProjectsProperties;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.query.model.Project;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@Repository
class ProjectQueryRepository {

    private final Map<ProjectKey, Project> projects;


    public ProjectQueryRepository(ProjectsProperties projectsProperties) {
        Map<ProjectKey, Project> projects = new HashMap<>();
        for (Map.Entry<ProjectKey, ProjectsProperties.Project> entry : projectsProperties.getProjects().entrySet()) {
            projects.put(entry.getKey(), new Project(
                    entry.getKey(),
                    entry.getValue().getTelegramChatId()
            ));
        }
        this.projects = Collections.unmodifiableMap(projects);
    }


    Optional<Project> findById(ProjectKey projectKey) {
        return Optional.ofNullable(projects.get(projectKey));
    }

}
