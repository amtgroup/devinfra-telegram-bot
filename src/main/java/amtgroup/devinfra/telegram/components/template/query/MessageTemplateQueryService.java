package amtgroup.devinfra.telegram.components.template.query;

import amtgroup.devinfra.telegram.components.template.exception.MessageTemplateFormatException;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQuery;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQueryResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Vitaly Ogoltsov
 */
public class MessageTemplateQueryService {

    private final Configuration freemarker;

    private final String suffix;


    public MessageTemplateQueryService(Configuration freemarker, String suffix) {
        Objects.requireNonNull(freemarker);
        Objects.requireNonNull(suffix);
        this.freemarker = freemarker;
        this.suffix = suffix;
    }


    /**
     * Отформатировать сообщение по шаблону.
     */
    public FormatMessageQueryResult formatMessage(@NotNull @Valid FormatMessageQuery query) {
        try {
            // получить шаблон
            Template template = freemarker.getTemplate(query.getTemplateId().toString() + suffix);
            // отформатировать сообщение по шаблону
            StringWriter writer = new StringWriter();
            template.process(query.getContext(), writer);
            String message = writer.toString();
            // убрать лишние пробелы и пустые строкии
            message = Arrays.stream(message.split("\n"))
                    .map(StringUtils::stripToNull)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("\n"));
            // вернуть результат
            return new FormatMessageQueryResult(
                    message
            );
        } catch (Exception e) {
            throw new MessageTemplateFormatException(query.getTemplateId(), e);
        }
    }

}
