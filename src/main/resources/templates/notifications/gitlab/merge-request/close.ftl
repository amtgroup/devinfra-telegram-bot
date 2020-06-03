<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestWebhookEvent" -->
<#include "../include/merge-request.ftl">

:recycle: <@merge_request_link/>
${markdown.bold(event.user.name)}: closed
