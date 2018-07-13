package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class ChangeRef {

    @NotBlank
    private String id;
    @NotBlank
    private String displayId;
    @NotBlank
    private String type;

}
