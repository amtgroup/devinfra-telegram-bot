package amtgroup.devinfra.telegram.components.telegram.services.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * @author Vitaly Ogoltsov
 */
@JsonClassDescription("Сообщение для отправки через Integram WebHook API")
@Data
public class IntegramMessage {

    @JsonPropertyDescription("Текст сообщения")
    private String text;

}
