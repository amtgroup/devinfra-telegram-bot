package amtgroup.devinfra.telegram.components.project.query;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import amtgroup.devinfra.telegram.components.project.query.model.Project;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Vitaly Ogoltsov
 */
public class ProjectCatalogQueryRepository {

    private final Map<ProjectKey, Project> projects;


    public ProjectCatalogQueryRepository(Collection<Project> projects) {
        this.projects = Collections.unmodifiableMap(
                projects.stream().collect(Collectors.toMap(Project::getProjectKey, Function.identity()))
        );
    }


    Optional<Project> findById(ProjectKey projectKey) {
        return Optional.ofNullable(projects.get(projectKey));
    }

}
