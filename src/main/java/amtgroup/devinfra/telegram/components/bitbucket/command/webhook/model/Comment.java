package amtgroup.devinfra.telegram.components.bitbucket.command.webhook.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class Comment {

    @NotBlank
    private String id;
    @NotBlank
    private String text;
    @NotNull
    @Valid
    private User author;

}
