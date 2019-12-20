package amtgroup.devinfra.telegram.components.project.exception;

import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
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
public class TelegramChatIdNotSetForProjectException extends ApplicationException {

    public TelegramChatIdNotSetForProjectException(ProjectKey projectKey) {
        super(projectKey);
    }

}
