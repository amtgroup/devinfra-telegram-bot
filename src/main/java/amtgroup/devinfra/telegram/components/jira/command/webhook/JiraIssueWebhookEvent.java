package amtgroup.devinfra.telegram.components.jira.command.webhook;

import amtgroup.devinfra.telegram.components.project.model.IssueKey;
import amtgroup.devinfra.telegram.components.project.model.ProjectKey;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vitaly Ogoltsov
 */
@Data
public class JiraIssueWebhookEvent {

    @NotBlank
    private String webhookEvent;

    @NotBlank
    private String issue_event_type_name;

    @NotNull
    @Valid
    private User user;

    @NotNull
    @Valid
    private Issue issue;

    @Valid
    private Changelog changelog;


    @Data
    public static class User {

        @NotBlank
        private String displayName;

    }

    @Data
    public static class Issue {

        @NotNull
        @Valid
        private IssueKey key;

        @NotNull
        private IssueFields fields;

    }

    @Data
    public static class IssueFields {

        @Valid
        private Project project;

        @Valid
        private Issue parent;

        @Valid
        private User reporter;

        @NotBlank
        private String summary;

        @NotBlank
        private IssueType issuetype;

    }

    @Data
    public static class Project {

        @NotNull
        @Valid
        private ProjectKey key;

        @NotBlank
        private String name;

    }

    @Data
    public static class IssueType {

        @NotBlank
        private String name;

        @NotNull
        private Boolean subtask;

    }

    @Data
    public static class Status {

        @NotBlank
        private String name;

    }

    @Data
    public static class Changelog {

        @NotEmpty
        @Valid
        private ChangelogItem[] items;

    }

    @Data
    public static class ChangelogItem {

        @NotBlank
        private String field;

        private String fromString;

        private String toString;

    }

}
