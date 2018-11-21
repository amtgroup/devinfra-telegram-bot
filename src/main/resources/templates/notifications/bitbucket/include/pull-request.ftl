<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="bitbucket" type="amtgroup.devinfra.telegram.components.bitbucket.config.BitBucketConfigurationProperties" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestWebhookEvent" -->

<#macro repo_link>${markdown.link(event.pullRequest.toRef.repository.name, bitbucket.url + '/projects/' + event.pullRequest.toRef.repository.project.key + '/repos/' + event.pullRequest.toRef.repository.slug)}</#macro>
<#macro pull_request_link><@repo_link/>: ${markdown.link('PR #' + event.pullRequest.id + ': ' + event.pullRequest.title, bitbucket.url + '/projects/' + event.pullRequest.toRef.repository.project.key + '/repos/' + event.pullRequest.toRef.repository.slug + '/pull-requests/' + event.pullRequest.id + '/overview')}</#macro>
