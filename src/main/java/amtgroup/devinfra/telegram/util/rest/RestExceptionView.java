package amtgroup.devinfra.telegram.util.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class RestExceptionView {

    private String message;
    private String details;

    @JsonIgnoreProperties({"message", "localizedMessage", "cause", "stackTrace"})
    private Throwable cause;

}
