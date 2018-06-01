package amtgroup.devinfra.telegram.util.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Утилитный класс для локализации исключений.
 *
 * @author Vitaly Ogoltsov
 */
@Component
final class ExceptionMessageUtils {

    private static MessageSource messageSource;

    /**
     * Возвращает локализованное сообщение об ошибке, используя источник сообщений по умолчанию.
     */
    static String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(messageSource, code, args, locale);
    }

    /**
     * Возвращает локализованное сообщение об ошибке.
     */
    static String getMessage(MessageSource messageSource, String code, Object[] args, Locale locale) {
        String message = null;
        if (messageSource != null) {
            try {
                message = messageSource.getMessage(code, args, locale);
            } catch (NoSuchMessageException e) {
                /* ignore */
            }
        }
        if (message == null) {
            message = code + ": " + ArrayUtils.toString(args);
        }
        return message;
    }


    /**
     * Специальный конструктор, необходимый для получения Spring {@link MessageSource}
     * для локализации сообщений об ошибках.
     */
    @Autowired
    protected ExceptionMessageUtils(MessageSource messageSource) {
        ExceptionMessageUtils.messageSource = messageSource;
    }

}
