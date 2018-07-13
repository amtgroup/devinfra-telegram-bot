package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class User {

    @NotBlank
    private String displayName;

}
