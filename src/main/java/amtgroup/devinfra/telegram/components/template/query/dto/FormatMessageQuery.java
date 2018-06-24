package amtgroup.devinfra.telegram.components.template.query.dto;

import amtgroup.devinfra.telegram.components.template.model.MessageTemplateId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Vitaly Ogoltsov
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FormatMessageQuery {

    @NotNull
    @Valid
    private MessageTemplateId templateId;

    @NotNull
    @Valid
    private Map<String, Object> context;

}
