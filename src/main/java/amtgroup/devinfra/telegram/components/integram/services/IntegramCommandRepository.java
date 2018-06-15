package amtgroup.devinfra.telegram.components.integram.services;

import amtgroup.devinfra.telegram.components.integram.services.model.ChatId;
import amtgroup.devinfra.telegram.components.integram.services.model.IntegramMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Vitaly Ogoltsov
 */
@FeignClient(value = "integram-command-repository", url = "https://integram.org/webhook/", decode404 = true)
public interface IntegramCommandRepository {

    @RequestMapping(method = RequestMethod.GET, path = "/{chatId}")
    void send(@PathVariable("chatId") ChatId chatId, @RequestBody IntegramMessage message);

}
