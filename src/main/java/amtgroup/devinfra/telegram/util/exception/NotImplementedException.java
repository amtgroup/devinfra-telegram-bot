package amtgroup.devinfra.telegram.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitaly Ogoltsov
 */
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends ApplicationException {

    public NotImplementedException() {
    }

}
