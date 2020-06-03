package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Repository {

    @NotBlank
    private String name;
    @NotBlank
    private String url;
    private String description;
    private String homepage;
}
