<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent" -->
<#include "include/issues.ftl">

:new: <@issue_type_icon/> <@issue_url/>
${markdown.bold('Reporter')}: ${markdown.escape(event.issue.fields.reporter.displayName)}
