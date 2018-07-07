package amtgroup.devinfra.telegram.components.template.engine;

import amtgroup.devinfra.telegram.components.template.util.Markdown;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

/**
 * @author Vitaly Ogoltsov
 */
public class MarkdownDialect extends AbstractDialect implements IExpressionObjectDialect {

    private static final String NAME = "Telegram Markdown";

    public MarkdownDialect() {
        super(NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("markdown");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new Markdown();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }

        };
    }

}
