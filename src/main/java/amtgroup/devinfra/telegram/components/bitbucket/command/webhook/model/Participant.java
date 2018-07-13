package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class Participant {

    @NotNull
    @Valid
    private User user;
    @NotBlank
    private String role;
    @NotNull
    private Boolean approved;
    @NotBlank
    private String status;

}
