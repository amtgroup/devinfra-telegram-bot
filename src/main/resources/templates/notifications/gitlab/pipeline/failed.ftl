<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabPipelineWebhookEvent" -->
<#include "../include/pipeline.ftl">

:red_circle: <@pipeline_link/>
${markdown.bold(event.user.name)}: failed
