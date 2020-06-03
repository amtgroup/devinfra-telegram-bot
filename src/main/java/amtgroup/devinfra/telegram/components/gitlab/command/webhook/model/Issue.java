package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Issue {
    @NotBlank
    private String id;
    @NotBlank
    private String title;
    private String description;
    private String branchName;

    @NotBlank
    private String state;

    @Valid
    private List<Label> labels;

    @Data
    public static class Label {
        @NotBlank
        private String id;
        @NotBlank
        private String title;
        @NotBlank
        private String color;
        private String description;
        @NotBlank
        private String type;
    }
}
