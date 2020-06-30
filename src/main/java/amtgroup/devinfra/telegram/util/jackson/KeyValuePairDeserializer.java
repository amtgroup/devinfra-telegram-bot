package amtgroup.devinfra.telegram.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Lukyanets
 */
public class KeyValuePairDeserializer extends JsonDeserializer<Map<Object, Object>> implements ContextualDeserializer {

    private Class<?> keyAs;

    private Class<?> contentAs;

    @Override
    public Map<Object, Object> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return this.deserialize(p, ctxt, new HashMap<>());
    }

    @Override
    public Map<Object, Object> deserialize(JsonParser p, DeserializationContext ctxt,
                                           Map<Object, Object> intoValue) throws IOException {
        JsonNode node = p.readValueAsTree();
        ObjectCodec codec = p.getCodec();
        if (node.isArray()) {
            node.forEach(entry -> {
                try {
                    JsonNode keyNode = entry.get("key");
                    JsonNode valueNode = entry.get("value");
                    intoValue.put(keyNode.traverse(codec).readValueAs(this.keyAs),
                            valueNode.traverse(codec).readValueAs(this.contentAs));
                } catch (NullPointerException | IOException e) {
                    // skip entry
                }
            });
        }
        return intoValue;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        JsonDeserialize jsonDeserialize = property.getAnnotation(JsonDeserialize.class);
        this.keyAs = jsonDeserialize.keyAs();
        this.contentAs = jsonDeserialize.contentAs();
        return this;
    }

}
