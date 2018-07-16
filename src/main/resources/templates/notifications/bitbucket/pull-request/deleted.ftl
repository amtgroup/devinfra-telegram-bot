<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestWebhookEvent" -->
<#include "../include/pull-request.ftl">

:recycle: <@pull_request_link/>
${markdown.bold(event.actor.displayName)}: deleted
