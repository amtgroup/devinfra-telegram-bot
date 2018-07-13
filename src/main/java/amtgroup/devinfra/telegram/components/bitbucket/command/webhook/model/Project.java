package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class Project {

    @NotNull
    @Valid
    private ProjectKey key;

    @NotBlank
    private String name;

}
