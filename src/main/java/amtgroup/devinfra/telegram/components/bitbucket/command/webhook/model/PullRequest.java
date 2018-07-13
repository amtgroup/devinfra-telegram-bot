package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class PullRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String state;
    @NotNull
    private Boolean open;
    @NotNull
    private Boolean closed;

    @NotNull
    @Valid
    private RepositoryRef fromRef;
    @NotNull
    @Valid
    private RepositoryRef toRef;

    @NotNull
    @Valid
    private Participant author;
    @Valid
    private List<Participant> reviewers;
    @Valid
    private List<Participant> participants;

}
