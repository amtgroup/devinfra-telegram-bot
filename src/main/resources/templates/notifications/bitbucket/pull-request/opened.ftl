<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestWebhookEvent" -->
<#include "../include/pull-request.ftl">

:new: <@pull_request_link/>
${markdown.bold(event.actor.displayName)}: opened