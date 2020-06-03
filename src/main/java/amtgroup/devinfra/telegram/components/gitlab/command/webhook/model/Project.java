package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Project {

    @NotNull
    private String id;

    @NotBlank
    private String name;

    private String description;

    private String webUrl;

    private String url;

    private String namespace;

}
