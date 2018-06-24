package amtgroup.devinfra.telegram.components.telegram.exception;

import amtgroup.devinfra.telegram.util.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitaly Ogoltsov
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TelegramRemoteException extends ApplicationException {

    public TelegramRemoteException(Throwable cause) {
        super(cause);
    }

}
