package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class RepositoryRef {

    @NotBlank
    private String id;
    @NotBlank
    private String displayId;
    @NotBlank
    private String latestCommit;
    @NotNull
    @Valid
    private Repository repository;

}
