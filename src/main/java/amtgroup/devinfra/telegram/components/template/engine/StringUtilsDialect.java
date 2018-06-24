package amtgroup.devinfra.telegram.components.template.engine;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

/**
 * @author Vitaly Ogoltsov
 */
public class StringUtilsDialect extends AbstractDialect implements IExpressionObjectDialect {

    private static final String NAME = "StringUtils from Apache CommonsLang3";

    public StringUtilsDialect() {
        super(NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("stringUtils");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new StringUtils();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

}
