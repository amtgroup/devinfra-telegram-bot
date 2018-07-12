package amtgroup.devinfra.telegram.components.template.query;

import amtgroup.devinfra.telegram.components.template.config.MessageTemplateConfiguration;
import amtgroup.devinfra.telegram.components.template.exception.MessageTemplateFormatException;
import amtgroup.devinfra.telegram.components.template.model.MessageTemplateId;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQuery;
import amtgroup.devinfra.telegram.components.template.query.dto.FormatMessageQueryResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = MessageTemplateConfiguration.class,
        initializers = ConfigFileApplicationContextInitializer.class
)
public class MessageTemplateQueryServiceTest {

    @Autowired
    private MessageTemplateQueryService messageTemplateQueryService;


    @Test(expected = MessageTemplateFormatException.class)
    public void testTemplateNotFound() {
        messageTemplateQueryService.formatMessage(new FormatMessageQuery(
                MessageTemplateId.of("template-not-found"),
                new HashMap<>()
        ));
    }

    @Test
    public void testFormatMessage() {
        Map<String, Object> context = new HashMap<>();
        context.put("data", Arrays.asList("a", "b"));
        FormatMessageQueryResult result = messageTemplateQueryService.formatMessage(new FormatMessageQuery(
                MessageTemplateId.of("test"),
                context
        ));
        Assert.assertEquals("a, b", result.getText());
    }

}
