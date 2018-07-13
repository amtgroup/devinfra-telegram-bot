package amtgroup.devinfra.telegram.components.template.util;

import java.util.Optional;

/**
 * Класс для работы с Telegram Markdown.
 *
 * @author Vitaly Ogoltsov
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Markdown {

    /**
     * Экранирует markdown-симолы в передаваемой строке.
     */
    public String escape(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString()
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`");
    }

    /**
     * Экранирует передаваемую строку и выделяет жирным.
     */
    public String bold(Object value) {
        String s = escape(value);
        if (s != null) {
            s = "*" + s + "*";
        }
        return s;
    }

    /**
     * Экранирует передаваемую строку и выделяет курсивом.
     */
    public String italic(Object value) {
        String s = escape(value);
        if (s != null) {
            s = "_" + s + "_";
        }
        return s;
    }

    /**
     * Создает и возвращает именованную ссылку.
     */
    public String link(Object name, Object link) {
        return "[" + Optional.ofNullable(escape(name)).orElse("") + "](" + escape(link) + ")";
    }

}
