package amtgroup.devinfra.telegram.components.template.query;

import amtgroup.devinfra.telegram.components.template.exception.MessageTemplateFormatException;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQuery;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQueryResult;
import amtgroup.devinfra.telegram.components.template.engine.MessageTemplateJsonPropertyAccessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.thymeleaf.spring5.expression.ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME;

/**
 * @author Vitaly Ogoltsov
 */
@Component
public class MessageTemplateQueryService {

    private final TemplateEngine templateEngine;
    private final List<PropertyAccessor> propertyAccessors;


    @Autowired
    public MessageTemplateQueryService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.propertyAccessors = Collections.singletonList(new MessageTemplateJsonPropertyAccessor());
    }


    /**
     * Отформатировать сообщение по шаблону.
     */
    public FormatMessageQueryResult formatMessage(@NotNull @Valid FormatMessageQuery query) {
        try {
            Context context = new Context(
                    Locale.getDefault(),
                    query.getContext()
            );
            // настройка evaluation context позволяет обращаться к полям JsonNode через "." вместо использования API-методов
            StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
            evaluationContext.setPropertyAccessors(new ArrayList<>(this.propertyAccessors));
            context.setVariable(THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME, evaluationContext);
            // отформатировать данные по шаблону
            String message = templateEngine.process(new TemplateSpec(query.getTemplateId().toString(), TemplateMode.TEXT), context);
            // убрать лишние пробелы и вернуть результат
            message = StringUtils.stripToNull(message);
            return new FormatMessageQueryResult(
                    message
            );
        } catch (Exception e) {
            throw new MessageTemplateFormatException(query.getTemplateId(), e);
        }
    }

}
