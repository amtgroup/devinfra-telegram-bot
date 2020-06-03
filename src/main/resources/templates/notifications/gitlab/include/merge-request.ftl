<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="gitlab" type="amtgroup.devinfra.telegram.components.gitlab.config.GitlabConfigurationProperties" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestWebhookEvent" -->

<#macro repo_link>${markdown.link(event.repository.name, event.repository.url)}</#macro>
<#macro merge_request_link><@repo_link/>: ${markdown.link('MR #' + event.objectAttributes.id + ': ' + event.objectAttributes.title, event.objectAttributes.url)}</#macro>
