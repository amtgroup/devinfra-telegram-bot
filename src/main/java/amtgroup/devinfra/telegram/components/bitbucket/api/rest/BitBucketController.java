package amtgroup.devinfra.telegram.components.bitbucket.api.rest;

import amtgroup.devinfra.telegram.components.bitbucket.command.BitbucketWebhookCommandService;
import amtgroup.devinfra.telegram.components.bitbucket.command.dto.HandleBitbucketWebhookEventCommand;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaly Ogoltsov
 */
@RestController
@RequestMapping(value = "/api/bitbucket", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description = "BitBucket Web Hooks", tags = "bitbucket-web-hooks")
@Slf4j
public class BitBucketController {

    private final BitbucketWebhookCommandService bitbucketWebhookCommandService;


    @Autowired
    public BitBucketController(BitbucketWebhookCommandService bitbucketWebhookCommandService) {
        this.bitbucketWebhookCommandService = bitbucketWebhookCommandService;
    }


    @ApiOperation("WebHook-уведомления от BitBucket")
    @PostMapping("/web-hook")
    public void handle(@RequestBody JsonNode event) {
        bitbucketWebhookCommandService.handle(new HandleBitbucketWebhookEventCommand(
                event
        ));
    }

}
