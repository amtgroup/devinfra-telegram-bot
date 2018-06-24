package amtgroup.devinfra.telegram.util.rest;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

/**
 * @author Vitaly Ogoltsov
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        Throwable resolvedException = ex;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        // пробежаться по дереву исключений и найти
        for (Throwable cause = ex; cause != null; cause = cause.getCause()) {
            if (ex instanceof ResponseStatusException) {
                resolvedException = ex;
                status = ((ResponseStatusException) ex).getStatus();
            }

            ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
            if (responseStatus != null) {
                resolvedException = cause;
                status = responseStatus.value();
            }
        }
        // запиисать ошибку в журнал
        if (status.is5xxServerError()) {
            logger.error("Непредвиденная ошибка при обработке REST-запроса", ex);
        } else {
            logger.warn("Ошибка при обработке REST-запроса", ex);
        }
        // ответить клиенту сообщением об ошибке
        RestExceptionView view = new RestExceptionView();
        view.setMessage(Optional.ofNullable(resolvedException.getMessage()).orElse(resolvedException.getClass().getName()));
        view.setCause(resolvedException);
        return handleExceptionInternal(
                ex,
                view,
                new HttpHeaders(),
                status,
                request
        );
    }

}
