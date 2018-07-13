<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestCommentWebhookEvent" -->
<#include "../include/pull-request-comment.ftl">

:bookmark: <@pull_request_comment_link/>
${markdown.bold(event.comment.author.displayName)} (deleted<#if event.comment.author.displayName != event.actor.displayName>by ${markdown.escape(event.actor.displayName)}</#if>): ${markdown.escape(event.comment.text)}
