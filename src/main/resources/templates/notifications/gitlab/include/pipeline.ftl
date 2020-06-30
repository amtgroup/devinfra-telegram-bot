<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="gitlab" type="amtgroup.devinfra.telegram.components.gitlab.config.GitlabConfigurationProperties" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabPipelineWebhookEvent" -->

<#macro project_link>${markdown.link(event.project.name, event.project.webUrl)}</#macro>
<#macro pipeline_link><@project_link/>: ${markdown.link('PL #' + event.objectAttributes.id + ': ' + event.objectAttributes.ref, event.project.webUrl + '/-/pipelines/' + event.objectAttributes.id)}</#macro>
