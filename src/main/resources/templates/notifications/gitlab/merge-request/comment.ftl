<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.gitlab.command.webhook.GitlabMergeRequestCommentWebhookEvent" -->
<#include "../include/merge-request-comment.ftl">

:bookmark: <@merge_request_comment_link/>
${markdown.bold(event.user.name)}: ${markdown.escape(event.objectAttributes.note)}
