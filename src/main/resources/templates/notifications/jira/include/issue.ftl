<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="jira" type="amtgroup.devinfra.telegram.components.jira.config.JiraConfigurationProperties" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.jira.command.webhook.JiraIssueWebhookEvent" -->
<#macro issue_type_icon>
    <#switch event.issue.fields.issuetype.name>
        <#case "Bug">:red_circle:<#break>
        <#default>:stars:<#break>
    </#switch>
</#macro>
<#macro issue_url>${markdown.link(event.issue.key + ': ' + event.issue.fields.summary, jira.url + '/browse/' + event.issue.key)}</#macro>
