<#-- @ftlvariable name="markdown" type="amtgroup.devinfra.telegram.components.template.util.Markdown" -->
<#-- @ftlvariable name="event" type="amtgroup.devinfra.telegram.components.bitbucket.command.webhook.BitbucketPullRequestCommentWebhookEvent" -->

<#macro pull_request_comment_link>${markdown.link(event.pullRequest.toRef.repository.name + ' #' + event.pullRequest.id + ': ' + event.pullRequest.title, 'https://git.example.com/projects/' + event.pullRequest.toRef.repository.project.key + '/repos/' + event.pullRequest.toRef.repository.slug + '/pull-requests/' + event.pullRequest.id + '/overview?commentId=' + event.comment.id?url)}</#macro>
