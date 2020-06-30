package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Build {
    @NotNull
    private String id;
    @NotNull
    private String stage;
    @NotNull
    private String name;
    @NotNull
    private String status;

    private String when;
    private boolean manual;
    private boolean allowFailure;

    @Valid
    private User user;

    private Runner runner;

    private ArtifactsFile artifactsFile;

    @Data
    public static class ArtifactsFile {
        private String filename;
        private String size;
    }

    @Data
    public static class Runner {
        @NotNull
        private String id;
        @NotNull
        private String description;
        private boolean active;
        private boolean isShared;
    }
}
