package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class RepositoryChange {

    @NotNull
    @Valid
    private ChangeRef ref;
    @NotBlank
    private String type;
    @NotBlank
    private String fromHash;
    @NotBlank
    private String toHash;

}
