package amtgroup.devinfra.telegram.util.exception;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Базовый класс для unchecked-исключений шлюза.
 *
 * @author Vitaly Ogoltsov
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ApplicationException extends RuntimeException {
    private final String code;
    private final Object[] args;


    /**
     * Создает исключение с заданными аргументами и кодом по умолчанию.
     */
    public ApplicationException(Object... args) {
        this(null, args, null);
    }

    public ApplicationException(Throwable throwable, Object... args) {
        this(null, args, throwable);
    }

    public ApplicationException(String code, Object[] args) {
        this(code, args, null);
    }

    public ApplicationException(String code, Object[] args, Throwable cause) {
        super(null, cause);
        this.code = code != null ? code : getClass().getSimpleName();
        this.args = args;
        setMessage(getLocalizedMessage(Locale.getDefault()));
    }


    private void setMessage(String message) {
        try {
            FieldUtils.writeField(this, "detailMessage", message, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Не удалось установить сообщение об ошибке", e);
        }
    }

    /**
     * Возвращает текст локализованного сообщения об ошибке.
     *
     * @param locale locale, используемая для получения локализованного текста сообщения
     * @return локализованное сообщение об ошибке
     */
    public String getLocalizedMessage(Locale locale) {
        return ExceptionMessageUtils.getMessage(getLocalizedMessageCode(), getLocalizedMessageArguments(), locale);
    }

    /**
     * Возвращает текст локализованного сообщения об ошибке.
     *
     * @param messageSource источник сообщений spring
     * @param locale        locale, используемая для получения локализованного текста сообщения
     * @return локализованное сообщение об ошибке
     */
    public String getLocalizedMessage(MessageSource messageSource, Locale locale) {
        return ExceptionMessageUtils.getMessage(messageSource, getLocalizedMessageCode(), getLocalizedMessageArguments(), locale);
    }

    /**
     * Код сообщения в {@link MessageSource}.
     *
     * @return код сообщения в {@link MessageSource}
     */
    protected String getLocalizedMessageCode() {
        return code;
    }

    /**
     * Аргументы для параметризации текста сообщения об ошибке.
     *
     * @return аргументы сообщения об ошибке
     */
    protected Object[] getLocalizedMessageArguments() {
        return args;
    }
}
