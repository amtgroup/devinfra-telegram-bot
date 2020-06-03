package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Commit {
    @NotNull
    private String id;
    @NotNull
    private String message;
    @NotNull
    private String url;
    @NotNull
    @Valid
    private CommitUser author;

    @Data
    public static class CommitUser {
        @NotBlank
        private String name;
        private String email;
    }

}
