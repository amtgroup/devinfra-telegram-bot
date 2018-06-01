package amtgroup.devinfra.telegram.components.jira.api.rest.dto;

import amtgroup.devinfra.telegram.components.jira.domain.JiraIssueKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Vitaly Ogoltsov
 */
@ApiModel(value = "JiraIssueEventRequest", description = "Структура уведомления о событии, связанном с задачей в JIRA")
@Data
public class JiraIssueEventRequest {

    @ApiModelProperty(value = "Всемя события события", position = 10)
    private ZonedDateTime timestamp;
    @ApiModelProperty(value = "Код события в JIRA", position = 10)
    private String webhookEvent;

    @ApiModelProperty(value = "Информация о задаче в JIRA", position = 20)
    private Issue issue;
    @ApiModelProperty(value = "Информация о пользователе, совершившем действие", position = 21)
    private User user;
    @ApiModelProperty(value = "Информация об изменениях по задаче", position = 22)
    private Changelog changelog;
    @ApiModelProperty(value = "Комментарий к изменению", position = 23)
    private Comment comment;


    @ApiModel(value = "JiraIssueEventRequest.Issue", description = "Информация о задаче в JIRA")
    @Data
    public static class Issue {


        @ApiModelProperty(value = "URL задачи", position = 10)
        private String self;
        @ApiModelProperty(value = "Идентификатор задачи", position = 11)
        private String id;
        @ApiModelProperty(value = "Код задачи", position = 12)
        private JiraIssueKey key;

    }

    @ApiModel(value = "JiraIssueEventRequest.User", description = "Информация о пользователе")
    @Data
    public static class User {

        @ApiModelProperty(value = "URL профиля пользователя", position = 10)
        private String self;
        @ApiModelProperty(value = "Логин", position = 11)
        private String name;

        @ApiModelProperty(value = "Имя пользователя", position = 20)
        private String displayName;
        @ApiModelProperty(value = "E-mail адрес", position = 21)
        private String emailAddress;

    }

    @ApiModel(value = "JiraIssueEventRequest.Changelog", description = "Информация об изменениях по задаче")
    @Data
    public static class Changelog {

        @ApiModelProperty(value = "Список изменений", position = 10)
        private List<Item> items;


        @ApiModel(value = "JiraIssueEventRequest.Changelog.Item", description = "Информация о пользователе")
        @Data
        public static class Item {

            @ApiModelProperty(value = "Тип поля", position = 10)
            private String field;
            @ApiModelProperty(value = "Тип поля", position = 11)
            @JsonProperty("fieldtype")
            private String fieldType;

            @ApiModelProperty(value = "Старое значение (код)", position = 20)
            private String from;
            @ApiModelProperty(value = "Старое значение (текст)", position = 21)
            private String fromString;

            @ApiModelProperty(value = "Новое значение (код)", position = 30)
            private String to;
            @ApiModelProperty(value = "Новое значение (текст)", position = 31)
            private String toString;

        }

    }

    @ApiModel(value = "JiraIssueEventRequest.Comment", description = "Комментарий к изменению")
    @Data
    public static class Comment {

        @ApiModelProperty(value = "URL комментария", position = 10)
        private String self;
        @ApiModelProperty(value = "Идентификатор комментария", position = 11)
        private String id;


        @ApiModelProperty(value = "Автор", position = 20)
        private User author;
        @ApiModelProperty(value = "Дата и время создания", position = 20)
        private ZonedDateTime created;

        @ApiModelProperty(value = "Автор последнего изменения", position = 20)
        private User updateAuthor;
        @ApiModelProperty(value = "Дата и время последнего изменения", position = 20)
        private ZonedDateTime updated;

        @ApiModelProperty(value = "Тело комментария", position = 20)
        private String body;

    }

}
