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
public class Snippet {
    @NotBlank
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String fileName;
    @NotBlank
    private String type;
}
