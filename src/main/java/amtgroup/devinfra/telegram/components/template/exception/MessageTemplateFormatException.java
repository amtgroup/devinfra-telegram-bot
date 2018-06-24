package amtgroup.devinfra.telegram.components.template.exception;

import amtgroup.devinfra.telegram.components.template.model.MessageTemplateId;
import amtgroup.devinfra.telegram.util.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitaly Ogoltsov
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class MessageTemplateFormatException extends ApplicationException {

    private final MessageTemplateId messageTemplateId;

    public MessageTemplateFormatException(MessageTemplateId messageTemplateId, Throwable cause) {
        super(cause, messageTemplateId);
        this.messageTemplateId = messageTemplateId;
    }

}
