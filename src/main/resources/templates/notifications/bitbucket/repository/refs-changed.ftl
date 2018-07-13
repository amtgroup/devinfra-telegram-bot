<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketRepositoryWebhookEvent" -->
<#include "../include/repository.ftl">

<#list event.changes as change>
:up: ${markdown.bold(event.actor.displayName)} :arrow_right: ${markdown.link(event.repository.name + ':' + change.ref.displayId, 'https://git.example.com/projects/' + event.repository.project.key + '/repos/' + event.repository.name + '/browse?at=' + change.ref.id?url)}
</#list>
