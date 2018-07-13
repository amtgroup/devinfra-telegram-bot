package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class Repository {

    @NotBlank
    private String slug;
    @NotBlank
    private String name;
    @NotNull
    @Valid
    private Project project;

}
