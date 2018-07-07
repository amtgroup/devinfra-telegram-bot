package amtgroup.devinfra.telegram.components.template.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.integration.json.JsonPropertyAccessor;

/**
 * @author Vitaly Ogoltsov
 */
public class MessageTemplateJsonPropertyAccessor extends JsonPropertyAccessor {

    @Override
    public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
        // вернуть TypedValue.NULL в случае, если считываемый узел типа NullNode
        TypedValue typedValue = super.read(context, target, name);
        if (typedValue.getValue() instanceof WrappedJsonNode) {
            JsonNode targetNode = ((WrappedJsonNode) typedValue.getValue()).getTarget();
            if (targetNode.getNodeType() == JsonNodeType.NULL) {
                return TypedValue.NULL;
            }
        }
        return typedValue;
    }

}
