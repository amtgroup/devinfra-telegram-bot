<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="strings" type="org.apache.commons.lang3.StringUtils" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent" -->
<#include "include/issues.ftl">

<#assign status_changed=false>
<#assign assignee_changed=false>
<#list event.changelog.items as changelog>
    <#if changelog.field == 'status'>
        <#assign status_changed=true>
    </#if>
    <#if changelog.field == 'assignee'>
        <#assign assignee_changed=true>
    </#if>
</#list>

<#if status_changed == true || assignee_changed == true>
<@issue_type_icon/> <@issue_url/>
<#list event.changelog.items?sort_by('field') as changelog>
${markdown.bold(strings.capitalize(changelog.field))}: ${markdown.escape(changelog.getFromString())!} :arrow_right: ${markdown.escape(changelog.getToString())!}
</#list>
${markdown.bold('Actor')}: ${markdown.escape(event.user.displayName)}
</#if>
