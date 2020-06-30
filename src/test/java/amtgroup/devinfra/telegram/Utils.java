package amtgroup.devinfra.telegram;

import lombok.NonNull;

/**
 * @author Sergey Lukyanets
 */
public final class Utils {
    /**
     * Заменяет окончания строки на системные (актуально для Windows - гит при пуле меняет окончание строк на виндовое)
     * @param str - форматироуемая строка
     * @return - результат форматирования
     */
    public static String lsFormat(@NonNull String str) {
        return str.replaceAll("\\n|\\r\\n", System.lineSeparator());
    }
}
