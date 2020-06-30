package amtgroup.devinfra.telegram.components.gitlab.command.webhook.model;

import amtgroup.devinfra.telegram.util.jackson.KeyValuePairDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Lukyanets
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Pipeline {

    @NotBlank
    private String id;
    @NotBlank
    private String ref;
    private boolean tag;
    @NotBlank
    private String sha;
    private String beforeSha;
    @NotBlank
    private String source;
    @NotBlank
    private String status;

    @NotNull
    private List<String> stages;

    private Integer duration;
    @JsonDeserialize(using = KeyValuePairDeserializer.class, keyAs = String.class, contentAs = String.class)
    private Map<String, String> variables;
}
