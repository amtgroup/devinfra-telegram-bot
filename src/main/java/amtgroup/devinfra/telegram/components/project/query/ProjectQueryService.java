package amtgroup.devinfra.telegram.components.project.query;

import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQuery;
import amtgroup.devinfra.telegram.components.project.query.dto.FindTelegramChatIdByProjectKeyQueryResult;
import amtgroup.devinfra.telegram.components.project.query.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Service
@Validated
@Slf4j
public class ProjectQueryService {

    private final ProjectQueryRepository repository;


    @Autowired
    public ProjectQueryService(ProjectQueryRepository repository) {
        this.repository = repository;
    }


    public FindTelegramChatIdByProjectKeyQueryResult handle(@NotNull @Valid FindTelegramChatIdByProjectKeyQuery query) {
        return new FindTelegramChatIdByProjectKeyQueryResult(
                repository.findById(query.getProjectKey())
                        .map(Project::getTelegramChatId)
                        .orElse(null)
        );
    }

}
